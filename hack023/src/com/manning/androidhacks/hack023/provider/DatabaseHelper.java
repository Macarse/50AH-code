/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
  private static final String TAG = DatabaseHelper.class
      .getCanonicalName();
  public static final String DATABASE_NAME = "todo.db";
  private static final int DATABASE_VERSION = 1;

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE " + TodoContentProvider.TODO_TABLE_NAME
        + " (" + TodoContentProvider.COLUMN_ID
        + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + TodoContentProvider.COLUMN_SERVER_ID + " INTEGER,"
        + TodoContentProvider.COLUMN_TITLE + " LONGTEXT,"
        + TodoContentProvider.COLUMN_STATUS_FLAG + " INTEGER" + ");");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion,
      int newVersion) {
    Log.i(TAG, "Upgrading database from version " + oldVersion + " to "
        + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS "
        + TodoContentProvider.TODO_TABLE_NAME);
    onCreate(db);
  }

}
