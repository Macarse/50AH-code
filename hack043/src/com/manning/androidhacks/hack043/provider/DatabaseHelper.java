/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack043.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
  public static final String DATABASE_NAME = "numbers.db";
  private static final int DATABASE_VERSION = 1;

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE "
        + NoBatchNumbersContentProvider.TABLE_NAME + " ("
        + NoBatchNumbersContentProvider.COLUMN_ID
        + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + NoBatchNumbersContentProvider.COLUMN_TEXT + " TEXT" + ");");

    db.execSQL("CREATE TABLE " + BatchNumbersContentProvider.TABLE_NAME
        + " (" + BatchNumbersContentProvider.COLUMN_ID
        + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + BatchNumbersContentProvider.COLUMN_TEXT + " TEXT" + ");");

    db.execSQL("CREATE TABLE " + MySQLContentProvider.TABLE_NAME + " ("
        + MySQLContentProvider.COLUMN_ID
        + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + MySQLContentProvider.COLUMN_TEXT + " TEXT" + ");");

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion,
      int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS "
        + NoBatchNumbersContentProvider.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS "
        + BatchNumbersContentProvider.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS "
        + MySQLContentProvider.TABLE_NAME);

    onCreate(db);
  }

}
