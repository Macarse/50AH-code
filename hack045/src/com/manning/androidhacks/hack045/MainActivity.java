/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack045;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

  private static final String PREFS_NAME = "main_activity_prefs";
  private static final String TIMES_OPENED_KEY = "times_opened_key";
  private static final String TIMES_OPENED_FMT = "Times opened: %d";

  private TextView mTextView;
  private int mTimesOpened;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.main);
    mTextView = (TextView) findViewById(R.id.times_opened);
  }

  @Override
  protected void onResume() {
    super.onResume();

    SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
    mTimesOpened = prefs.getInt(TIMES_OPENED_KEY, 1);
    mTextView.setText(String.format(TIMES_OPENED_FMT, mTimesOpened));
  }

  @Override
  protected void onPause() {
    super.onPause();

    Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
    editor.putInt(TIMES_OPENED_KEY, mTimesOpened + 1);
    SharedPreferencesCompat.apply(editor);
  }
}
