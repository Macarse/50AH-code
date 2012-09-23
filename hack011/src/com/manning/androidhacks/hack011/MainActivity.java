/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack011;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends Activity {

  private static final String DATE_FORMAT = "%02d:%02d:%02d";
  private static final int REFRESH_DELAY = 500;

  private final Handler mHandler = new Handler();
  private final Runnable mTimeRefresher = new Runnable() {
    @Override
    public void run() {
      final Date d = new Date();
      mTextView.setText(String.format(DATE_FORMAT, d.getHours(),
          d.getMinutes(), d.getSeconds()));
      mHandler.postDelayed(this, REFRESH_DELAY);
    }
  };

  private TextView mTextView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mTextView = (TextView) findViewById(R.id.main_clock_time);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mHandler.post(mTimeRefresher);
  }

  @Override
  protected void onStop() {
    super.onStop();
    mHandler.removeCallbacks(mTimeRefresher);
  }
}
