/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.manning.androidhacks.hack023.model.Todo;
import com.manning.androidhacks.hack023.provider.StatusFlag;
import com.manning.androidhacks.hack023.provider.TodoContentProvider;

public class TodoDAO {
  private static final TodoDAO instance = new TodoDAO();

  private TodoDAO() {
  }

  public static TodoDAO getInstance() {
    return instance;
  }

  public void addNewTodo(ContentResolver contentResolver, Todo item) {
    addNewTodo(contentResolver, item, StatusFlag.ADD);
  }

  public void addNewTodo(ContentResolver contentResolver, Todo list,
      int flag) {
    ContentValues contentValue = getTodoContentValues(list, flag);
    contentResolver.insert(TodoContentProvider.CONTENT_URI,
        contentValue);
  }

  private ContentValues getTodoContentValues(Todo todo, int flag) {
    ContentValues cv = new ContentValues();
    cv.put(TodoContentProvider.COLUMN_SERVER_ID, todo.getId());
    cv.put(TodoContentProvider.COLUMN_TITLE, todo.getTitle());
    cv.put(TodoContentProvider.COLUMN_STATUS_FLAG, flag);

    return cv;
  }

  public List<Todo> getCleanTodos(ContentResolver contentResolver) {
    return getTodosWithSelection(contentResolver,
        TodoContentProvider.COLUMN_STATUS_FLAG + " = "
            + StatusFlag.CLEAN);
  }

  private List<Todo> getTodosWithSelection(
      ContentResolver contentResolver, String selection) {
    Cursor cursor = contentResolver.query(
        TodoContentProvider.CONTENT_URI, null, selection, null, null);

    List<Todo> list = new ArrayList<Todo>();

    while (cursor.moveToNext()) {
      int localId = cursor.getInt(cursor
          .getColumnIndexOrThrow(TodoContentProvider.COLUMN_ID));
      int serverId = cursor.getInt(cursor
          .getColumnIndexOrThrow(TodoContentProvider.COLUMN_SERVER_ID));
      String name = cursor.getString(cursor
          .getColumnIndexOrThrow(TodoContentProvider.COLUMN_TITLE));
      int status = cursor
          .getInt(cursor
              .getColumnIndexOrThrow(TodoContentProvider.COLUMN_STATUS_FLAG));

      Todo currentTodo = new Todo();

      if (status == StatusFlag.ADD) {
        currentTodo.setId((long) localId);
      } else {
        currentTodo.setId((long) serverId);
      }
      currentTodo.setTitle(name);
      currentTodo.setStatus(status);

      list.add(currentTodo);
    }

    cursor.close();
    return list;
  }

  public int deleteTodo(ContentResolver contentResolver, Long id) {
    int ret = 0;

    /* Using the local id */
    int status = getTodoStatus(contentResolver, id);

    switch (status) {
    case StatusFlag.ADD:
      ret = contentResolver.delete(TodoContentProvider.CONTENT_URI,
          TodoContentProvider.COLUMN_ID + "=" + id, null);
      break;
    case StatusFlag.MOD:
    case StatusFlag.CLEAN:
      ContentValues cv = new ContentValues();
      cv.put(TodoContentProvider.COLUMN_STATUS_FLAG, StatusFlag.DELETE);
      contentResolver.update(TodoContentProvider.CONTENT_URI, cv,
          TodoContentProvider.COLUMN_ID + "=" + id, null);
      break;
    default:
      throw new RuntimeException(
          "Tried to delete a todo with invalid status");
    }

    return ret;
  }

  public int getTodoStatus(ContentResolver contentResolver, Long id) {
    Cursor c = contentResolver.query(TodoContentProvider.CONTENT_URI,
        null, TodoContentProvider.COLUMN_ID + "=" + id, null, null);

    int status = 0;

    try {
      if (c.moveToNext()) {
        status = c
            .getInt(c
                .getColumnIndexOrThrow(TodoContentProvider.COLUMN_STATUS_FLAG));

      } else {
        throw new RuntimeException(
            "Tried to delete a non existent todo");
      }
    } finally {
      c.close();
    }

    return status;
  }

  public int forcedDeleteTodo(ContentResolver contentResolver, Long id) {
    return contentResolver.delete(TodoContentProvider.CONTENT_URI,
        TodoContentProvider.COLUMN_SERVER_ID + "=" + id, null);
  }

  public Todo isTodoInDb(ContentResolver contentResolver, Long serverId) {
    Todo todo = null;

    Cursor cursor = contentResolver.query(
        TodoContentProvider.CONTENT_URI, null,
        TodoContentProvider.COLUMN_SERVER_ID + "=" + serverId, null,
        null);

    if (cursor.moveToNext()) {
      String name = cursor.getString(cursor
          .getColumnIndexOrThrow(TodoContentProvider.COLUMN_TITLE));
      int status = cursor
          .getInt(cursor
              .getColumnIndexOrThrow(TodoContentProvider.COLUMN_STATUS_FLAG));

      todo = new Todo();
      todo.setId(serverId);
      todo.setTitle(name);
      todo.setStatus(status);
    }

    cursor.close();
    return todo;
  }

  public void modifyTodoFromServer(ContentResolver contentResolver,
      Todo list) {
    ContentValues cv = new ContentValues();
    cv.put(TodoContentProvider.COLUMN_TITLE, list.getTitle());
    cv.put(TodoContentProvider.COLUMN_STATUS_FLAG, StatusFlag.CLEAN);

    contentResolver
        .update(TodoContentProvider.CONTENT_URI, cv,
            TodoContentProvider.COLUMN_SERVER_ID + "=" + list.getId(),
            null);

  }

  public List<Todo> getDirtyList(ContentResolver mContentResolver) {
    return getTodosWithSelection(mContentResolver,
        TodoContentProvider.COLUMN_STATUS_FLAG + " != "
            + StatusFlag.CLEAN);
  }

  public void clearAdd(ContentResolver contentResolver, long id,
      Todo serverTodo) {
    ContentValues cv = getTodoContentValues(serverTodo,
        StatusFlag.CLEAN);
    contentResolver.update(TodoContentProvider.CONTENT_URI, cv,
        TodoContentProvider.COLUMN_ID + "=" + id, null);

  }

  public int deleteTodoForced(ContentResolver contentResolver, long id) {
    return contentResolver.delete(TodoContentProvider.CONTENT_URI,
        TodoContentProvider.COLUMN_SERVER_ID + "=" + id, null);
  }

}
