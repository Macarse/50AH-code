/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack046;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.manning.androidhacks.hack046.helper.NotificationHelper;
import com.manning.androidhacks.hack046.service.MsgService;

public class ReplyActivity extends Activity {
  private EditText mEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reply);
    mEditText = (EditText) findViewById(R.id.activity_reply_msg);
  }

  @Override
  protected void onResume() {
    super.onResume();
    NotificationHelper.dismissMsgNotification(this);
  }

  public void onDiscardClick(View v) {
    finish();
  }

  public void onSendClick(View v) {
    String msg = mEditText.getText().toString();

    if (TextUtils.isEmpty(msg)) {
      Toast.makeText(this, "Empty msg!", Toast.LENGTH_SHORT).show();
      return;
    }

    Intent intent = new Intent(this, MsgService.class);
    intent.setAction(MsgService.MSG_REPLY);
    intent.putExtra(MsgService.MSG_REPLY_KEY, msg);
    startService(intent);

    finish();
  }
}
