/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.ParseException;
import org.apache.http.auth.AuthenticationException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.manning.androidhacks.hack023.api.TodoServiceImpl;
import com.manning.androidhacks.hack023.authenticator.AuthenticatorActivity;
import com.manning.androidhacks.hack023.dao.TodoDAO;
import com.manning.androidhacks.hack023.exception.AndroidHacksException;
import com.manning.androidhacks.hack023.model.Todo;
import com.manning.androidhacks.hack023.provider.StatusFlag;

public class TodoSyncAdapter extends AbstractThreadedSyncAdapter {

  private static final String TAG = TodoSyncAdapter.class
      .getCanonicalName();
  private final ContentResolver mContentResolver;
  private AccountManager mAccountManager;
  private final static TodoDAO mTodoDAO = TodoDAO.getInstance();

  public TodoSyncAdapter(Context context, boolean autoInitialize) {
    super(context, autoInitialize);
    mContentResolver = context.getContentResolver();
    mAccountManager = AccountManager.get(context);
  }

  @Override
  public void onPerformSync(Account account, Bundle extras,
      String authority, ContentProviderClient provider,
      SyncResult syncResult) {

    String authtoken = null;
    try {
      authtoken = mAccountManager.blockingGetAuthToken(account,
          AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE, true);

      List<Todo> data = fetchData();
      syncRemoteDeleted(data);
      syncFromServerToLocalStorage(data);
      syncDirtyToServer(mTodoDAO.getDirtyList(mContentResolver));

    } catch (Exception e) {
      handleException(authtoken, e, syncResult);
    }

  }

  protected void syncDirtyToServer(List<Todo> dirtyList)
      throws AuthenticationException, IOException,
      AndroidHacksException {
    for (Todo todo : dirtyList) {
      Log.d(TAG, "Dirty list: " + todo);

      switch (todo.getStatus()) {
      case StatusFlag.ADD:
        pushNewTodo(todo);
        break;
      case StatusFlag.MOD:
        throw new AndroidHacksException(
            "Todo title modification is not supported");
      case StatusFlag.DELETE:
        pushDeleteTodo(todo);
        break;
      default:
        throw new RuntimeException("Invalid status: "
            + todo.getStatus());
      }
    }
  }

  private void pushNewTodo(Todo todo) throws AuthenticationException,
      IOException, AndroidHacksException {
    Todo serverTodo = TodoServiceImpl.createTodo(todo.getTitle());
    mTodoDAO.clearAdd(mContentResolver, todo.getId(), serverTodo);
  }

  private void pushDeleteTodo(Todo todo)
      throws AuthenticationException, AndroidHacksException {
    TodoServiceImpl.deleteTodo(todo.getId());
    mTodoDAO.deleteTodoForced(mContentResolver, todo.getId());
  }

  protected void syncRemoteDeleted(List<Todo> remoteData) {
    Log.d(TAG, "Syncing remote deleted lists...");

    List<Todo> localClean = mTodoDAO.getCleanTodos(mContentResolver);
    for (Todo cleanTodo : localClean) {

      if (!remoteData.contains(cleanTodo)) {
        Log.d(TAG, "Todo with id " + cleanTodo.getId()
            + " has been deleted remotely.");
        mTodoDAO.forcedDeleteTodo(mContentResolver, cleanTodo.getId());
      }
    }
  }

  protected void syncFromServerToLocalStorage(List<Todo> data) {
    for (Todo todoFromServer : data) {
      Todo todoInDb = mTodoDAO.isTodoInDb(mContentResolver,
          todoFromServer.getId());

      if (todoInDb == null) {
        Log.d(TAG, "Adding new todo from server: " + todoFromServer);
        mTodoDAO.addNewTodo(mContentResolver, todoFromServer,
            StatusFlag.CLEAN);

      } else if (todoInDb.getStatus() == StatusFlag.CLEAN) {
        Log.d(TAG, "Modifying list from server: " + todoInDb);
        mTodoDAO.modifyTodoFromServer(mContentResolver, todoFromServer);
      }

    }
  }

  protected List<Todo> fetchData() throws AuthenticationException,
      AndroidHacksException, JsonParseException, IOException {
    List<Todo> list = TodoServiceImpl.fetchTodos();

    return list;
  }

  private void handleException(String authtoken, Exception e,
      SyncResult syncResult) {
    if (e instanceof AuthenticatorException) {
      syncResult.stats.numParseExceptions++;
      Log.e(TAG, "AuthenticatorException", e);
    } else if (e instanceof OperationCanceledException) {
      Log.e(TAG, "OperationCanceledExcepion", e);
    } else if (e instanceof IOException) {
      Log.e(TAG, "IOException", e);
      syncResult.stats.numIoExceptions++;
    } else if (e instanceof AuthenticationException) {
      mAccountManager.invalidateAuthToken(
          AuthenticatorActivity.PARAM_ACCOUNT_TYPE, authtoken);
      // The numAuthExceptions require user intervention and are
      // considered hard errors.
      // We automatically get a new hash, so let's make SyncManager retry
      // automatically.
      syncResult.stats.numIoExceptions++;
      Log.e(TAG, "AuthenticationException", e);
    } else if (e instanceof ParseException) {
      syncResult.stats.numParseExceptions++;
      Log.e(TAG, "ParseException", e);
    } else if (e instanceof JsonParseException) {
      syncResult.stats.numParseExceptions++;
      Log.e(TAG, "JSONException", e);
    } else if (e instanceof AndroidHacksException) {
      Log.e(TAG, "AndroidHacksException", e);
    }
  }

}
