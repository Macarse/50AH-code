/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack042.db;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.manning.androidhacks.hack042.model.Poi;

public class DatabaseHelper extends SQLiteOpenHelper {
  public static final String DATABASE_NAME = "pois.db";
  private static final int DATABASE_VERSION = 1;
  private Context mContext;

  static {
    System.loadLibrary("hack042-native");
  }

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    mContext = context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE " + "pois ("
        + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "title TEXT,"
        + "longitude FLOAT," + "latitude FLOAT);");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion,
      int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS pois;");

  }

  public List<Poi> getNear(float latitude, float longitude) {
    File file = mContext.getDatabasePath(DATABASE_NAME);
    return getNear(file.getAbsolutePath(), latitude, longitude);
  }

  private native List<Poi> getNear(String dbPath, float latitude,
      float longitude);
}
