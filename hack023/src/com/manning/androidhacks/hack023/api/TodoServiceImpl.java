/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.api;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import org.apache.http.auth.AuthenticationException;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.manning.androidhacks.hack023.exception.AndroidHacksException;
import com.manning.androidhacks.hack023.model.Todo;
import com.manning.androidhacks.hack023.net.HttpHelper;

public class TodoServiceImpl {
  private static final String TAG = TodoServiceImpl.class
      .getCanonicalName();

  public static List<Todo> fetchTodos() throws AuthenticationException,
      JsonParseException, IOException, AndroidHacksException {
    Log.d(TAG, "Fetching Todo's...");
    String url = AndroidHacksUrlFactory.getInstance().getTodoUrl();
    String response = HttpHelper.getHttpResponseAsString(url, null);
    Gson gson = new Gson();
    List<Todo> lists = gson.fromJson(response, getToken());

    return lists;
  }

  private static Type getToken() {
    return new TypeToken<List<Todo>>() {
    }.getType();
  }

  public static Todo createTodo(String title)
      throws AuthenticationException, JsonParseException, IOException,
      AndroidHacksException {
    Log.d(TAG, "Creating Todo list " + title);
    String urlFmt = AndroidHacksUrlFactory.getInstance()
        .getTodoAddUrlFmt();
    String url = String.format(urlFmt, title);
    String response = HttpHelper.getHttpResponseAsString(url, null);

    Gson gson = new Gson();
    List<Todo> lists = gson.fromJson(response, getToken());

    if (lists.size() != 1) {
      throw new AndroidHacksException("Error creating Todo " + title);
    }

    return lists.get(0);
  }

  public static void deleteTodo(long id)
      throws AuthenticationException, AndroidHacksException {
    Log.d(TAG, "Deleting Todo with id " + id);
    String urlFmt = AndroidHacksUrlFactory.getInstance()
        .getTodoDeleteUrlFmt();
    String url = String.format(urlFmt, id);
    HttpHelper.getHttpResponseAsString(url, null);
  }

}
