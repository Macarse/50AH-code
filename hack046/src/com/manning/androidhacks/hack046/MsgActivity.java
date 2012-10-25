/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack046;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.manning.androidhacks.hack046.helper.NotificationHelper;
import com.manning.androidhacks.hack046.service.MsgService;

public class MsgActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_msg);
  }

  @Override
  protected void onResume() {
    super.onResume();
    NotificationHelper.dismissMsgNotification(this);
  }

  public void onDeleteClick(View v) {
    Intent intent = new Intent(this, MsgService.class);
    intent.setAction(MsgService.MSG_DELETE);
    startService(intent);
    finish();
  }

  public void onReplyClick(View v) {
    startActivity(new Intent(this, ReplyActivity.class));
  }
}
