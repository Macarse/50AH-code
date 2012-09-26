/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack042;

import com.manning.androidhacks.hack042.db.DatabaseHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddPoiActivity extends Activity {
  private EditText mTitleEditText;
  private EditText mLatitudeEditText;
  private EditText mLongitudeEditText;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_poi);
    mTitleEditText = (EditText) findViewById(R.id.add_poi_title);
    mLatitudeEditText = (EditText) findViewById(R.id.add_poi_latitude);
    mLongitudeEditText = (EditText) findViewById(R.id.add_poi_longitude);
  }

  public void onAddClick(View v) {
    String title = mTitleEditText.getText().toString();
    String latitude = mLatitudeEditText.getText().toString();
    String longitude = mLongitudeEditText.getText().toString();

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    ContentValues cv = new ContentValues();
    cv.put("title", title);
    cv.put("latitude", latitude);
    cv.put("longitude", longitude);

    db.insert("pois", null, cv);
    finish();
  }
}
