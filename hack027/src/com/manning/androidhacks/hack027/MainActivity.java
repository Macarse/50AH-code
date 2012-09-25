/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack027;

import java.util.ArrayList;
import com.manning.androidhacks.hack027.NumbersAdapter.NumbersAdapterDelegate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements
    NumbersAdapterDelegate {
  private static final String TAG = MainActivity.class
      .getCanonicalName();

  private ListView mListView;
  private ArrayList<Integer> mNumbers;
  private NumbersAdapter mAdapter;
  private EditText mEditText;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mEditText = (EditText) findViewById(R.id.main_edittext);
    mListView = (ListView) findViewById(R.id.main_listview);
    mNumbers = new ArrayList<Integer>();
    mAdapter = new NumbersAdapter(this, mNumbers);
    mListView.setAdapter(mAdapter);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mAdapter.setDelegate(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mAdapter.setDelegate(null);
  }

  @Override
  public void removeItem(Integer value) {
    mNumbers.remove(value);
    Toast
        .makeText(this, "Removed object: " + value, Toast.LENGTH_SHORT)
        .show();
    mAdapter.notifyDataSetChanged();
  }

  public void addNumber(View v) {
    String value = mEditText.getText().toString().trim();
    try {
      mNumbers.add(Integer.valueOf(value));
      mEditText.setText("");
      mAdapter.notifyDataSetChanged();
    } catch (NumberFormatException e) {
      Log.e(TAG, "Couldn't convert to integer the string: " + value);
    }
  }
}
