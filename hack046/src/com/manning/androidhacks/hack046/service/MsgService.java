/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack046.service;

import com.manning.androidhacks.hack046.helper.NotificationHelper;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MsgService extends IntentService {
  private static final String TAG = MsgService.class.getCanonicalName();

  public static final String MSG_RECEIVE = "msg_receive";
  public static final String MSG_DELETE = "msg_delete";
  public static final String MSG_REPLY = "msg_reply";
  public static final String MSG_REPLY_KEY = "msg_reply_key";

  public MsgService() {
    super(TAG);
  }

  public MsgService(String name) {
    super(name);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    if ( MSG_RECEIVE.equals(intent.getAction()) ) {
      handleMsgReceive();
    } else if ( MSG_DELETE.equals(intent.getAction()) ) {
      handleMsgDelete();
    } else if ( MSG_REPLY.equals(intent.getAction()) ) {
      handleMsgReply(intent.getStringExtra(MSG_REPLY_KEY));
    }
  }

  private void handleMsgReply(String msg) {
    Log.d(TAG, "replying with msg: " + msg);
    NotificationHelper.dismissMsgNotification(this);
  }

  private void handleMsgDelete() {
    Log.d(TAG, "Removing msg...");
    NotificationHelper.dismissMsgNotification(this);
  }

  private void handleMsgReceive() {
    NotificationHelper.showMsgNotification(this);
  }

}
