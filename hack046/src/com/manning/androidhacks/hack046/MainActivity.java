/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack046;

import com.manning.androidhacks.hack046.service.MsgService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }

  public void onReceiveMsgClick(View v) {
    Intent intent = new Intent(this, MsgService.class);
    intent.setAction(MsgService.MSG_RECEIVE);
    startService(intent);
  }
}
