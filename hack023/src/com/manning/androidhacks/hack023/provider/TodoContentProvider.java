/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.provider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class TodoContentProvider extends ContentProvider {
  public static final String TODO_TABLE_NAME = "todos";
  public static final String AUTHORITY = TodoContentProvider.class
      .getCanonicalName();

  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_SERVER_ID = "server_id";
  public static final String COLUMN_TITLE = "title";
  public static final String COLUMN_STATUS_FLAG = "status_flag";

  private static final int TODO = 1;
  private static final int TODO_ID = 2;

  public static final String DEFAULT_SORT_ORDER = "_id ASC";

  private static HashMap<String, String> projectionMap;
  private static final UriMatcher sUriMatcher;

  public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.androidhacks.todo";
  public static final String CONTENT_TYPE_ID = "vnd.android.cursor.item/vnd.androidhacks.todo";

  public static final Uri CONTENT_URI = Uri.parse("content://"
      + AUTHORITY + "/" + TODO_TABLE_NAME);

  private DatabaseHelper dbHelper;

  static {
    sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    sUriMatcher.addURI(AUTHORITY, TODO_TABLE_NAME, TODO);
    sUriMatcher.addURI(AUTHORITY, TODO_TABLE_NAME + "/#", TODO_ID);

    projectionMap = new HashMap<String, String>();
    projectionMap.put(COLUMN_ID, COLUMN_ID);
    projectionMap.put(COLUMN_SERVER_ID, COLUMN_SERVER_ID);
    projectionMap.put(COLUMN_TITLE, COLUMN_TITLE);
    projectionMap.put(COLUMN_STATUS_FLAG, COLUMN_STATUS_FLAG);
  }

  @Override
  public boolean onCreate() {
    dbHelper = new DatabaseHelper(getContext());
    return true;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
      String[] selectionArgs, String sortOrder) {

    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    switch (sUriMatcher.match(uri)) {
    case TODO:
      qb.setTables(TODO_TABLE_NAME);
      qb.setProjectionMap(projectionMap);
      break;
    case TODO_ID:
      qb.setTables(TODO_TABLE_NAME);
      qb.setProjectionMap(projectionMap);
      qb.appendWhere(COLUMN_ID + "=" + uri.getPathSegments().get(1));
      break;
    default:
      throw new RuntimeException("Unknown URI");
    }

    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor c = qb.query(db, projection, selection, selectionArgs, null,
        null, sortOrder);

    c.setNotificationUri(getContext().getContentResolver(), uri);
    return c;
  }

  @Override
  public String getType(Uri uri) {
    switch (sUriMatcher.match(uri)) {
    case TODO:
      return CONTENT_TYPE;
    case TODO_ID:
      return CONTENT_TYPE_ID;
    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  @Override
  public Uri insert(Uri uri, ContentValues initialValues) {
    ContentValues values;
    if (initialValues != null) {
      values = new ContentValues(initialValues);
    } else {
      values = new ContentValues();
    }
    String table = null;
    String nullableCol = null;

    switch (sUriMatcher.match(uri)) {
    case TODO:
      table = TODO_TABLE_NAME;
      nullableCol = COLUMN_TITLE;
      break;
    default:
      new RuntimeException("Invalid URI for inserting: " + uri);
    }

    SQLiteDatabase db = dbHelper.getWritableDatabase();
    long rowId = db.insert(table, nullableCol, values);

    if (rowId > 0) {
      Uri noteUri = ContentUris.withAppendedId(uri, rowId);
      getContext().getContentResolver().notifyChange(noteUri, null);
      return noteUri;
    }

    throw new SQLException("Failed to insert row into " + uri);
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    int count;

    switch (sUriMatcher.match(uri)) {
    case TODO:
      count = db.delete(TODO_TABLE_NAME, selection, selectionArgs);
      break;
    case TODO_ID:
      String id = uri.getPathSegments().get(1);
      count = db.delete(TODO_TABLE_NAME, COLUMN_ID
          + "="
          + id
          + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")"
              : ""), selectionArgs);
      break;
    default:
      throw new RuntimeException("Unkown URI: " + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);
    return count;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection,
      String[] selectionArgs) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    int count = 0;
    switch (sUriMatcher.match(uri)) {
    case TODO:
      count = db.update(TODO_TABLE_NAME, values, selection,
          selectionArgs);
      break;
    case TODO_ID:
      count = db.update(
          TODO_TABLE_NAME,
          values,
          COLUMN_ID
              + "="
              + uri.getPathSegments().get(1)
              + (!TextUtils.isEmpty(selection) ? " AND (" + selection
                  + ")" : ""), selectionArgs);
      break;
    default:
      throw new RuntimeException("Unknown URI " + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);
    return count;
  }

}
