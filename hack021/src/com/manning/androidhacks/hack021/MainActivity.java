/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack021;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
  private ProgressDialog mProgressDialog;
  private TextView mTextView;

  private BroadcastReceiver mReceiver;
  private IntentFilter mIntentFilter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mTextView = (TextView) findViewById(R.id.text_view);

    mProgressDialog = ProgressDialog.show(this, "Loading",
        "Please wait");
    mProgressDialog.show();

    mReceiver = new MyServiceReceiver();
    mIntentFilter = new IntentFilter(MyService.ACTION);

    startService(new Intent(this, MyService.class));
  }

  @Override
  protected void onResume() {
    super.onResume();
    registerReceiver(mReceiver, mIntentFilter);
  }

  @Override
  public void onPause() {
    super.onPause();
    unregisterReceiver(mReceiver);
  }

  private void update(String msg) {
    mProgressDialog.dismiss();
    mTextView.setText(msg);
  }

  class MyServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      update(intent.getExtras().getString(MyService.MSG_KEY));

    }

  }
}
