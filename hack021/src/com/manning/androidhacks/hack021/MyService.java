/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack021;

import java.util.Date;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

public class MyService extends IntentService {

  public static final String ACTION = "com.manning.androidhacks.hack021.SERVICE_MSG";
  public static final String MSG_KEY = "MSG_KEY";

  public MyService() {
    super("MyService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    SystemClock.sleep(5000L);
    Intent broadcast = new Intent(ACTION);
    broadcast.putExtra(MSG_KEY, new Date().toGMTString());
    sendBroadcast(broadcast);
  }

}
