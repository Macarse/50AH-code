/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack042;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.manning.androidhacks.hack042.adapter.PoisAdapter;
import com.manning.androidhacks.hack042.db.DatabaseHelper;
import com.manning.androidhacks.hack042.model.Poi;

public class MainActivity extends Activity {
  private ListView mListView;
  private EditText mLatitudeEditText;
  private EditText mLongitudeEditText;
  private List<Poi> mPois;
  private PoisAdapter mAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mListView = (ListView) findViewById(R.id.main_listview);
    mPois = new ArrayList<Poi>();
    mAdapter = new PoisAdapter(this, 1, mPois);
    mListView.setAdapter(mAdapter);

    mLatitudeEditText = (EditText) findViewById(R.id.main_latitude);
    mLongitudeEditText = (EditText) findViewById(R.id.main_longitude);
  }

  public void onSearchClick(View v) {
    Float lat = null;
    Float lon = null;

    try {
      lat = new Float(mLatitudeEditText.getText().toString());
      lon = new Float(mLongitudeEditText.getText().toString());

    } catch (NumberFormatException e) {
      Toast.makeText(this,
          "Couldn't convert search fields to coordinates",
          Toast.LENGTH_SHORT).show();
      return;
    }

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    mPois.clear();
    List<Poi> pois = dbHelper.getNear(lat, lon);
    mPois.addAll(pois);
    mAdapter.notifyDataSetChanged();
  }

  public void onAddPoi(View v) {
    startActivity(new Intent(this, AddPoiActivity.class));
  }
}
