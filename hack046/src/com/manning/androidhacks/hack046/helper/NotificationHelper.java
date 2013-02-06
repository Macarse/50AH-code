/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack046.helper;

import com.manning.androidhacks.hack046.service.MsgService;
import com.manning.androidhacks.hack046.MsgActivity;
import com.manning.androidhacks.hack046.R;
import com.manning.androidhacks.hack046.ReplyActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper {

  public static void showMsgNotification(Context ctx) {
    final NotificationManager mgr;
    mgr = (NotificationManager) ctx
        .getSystemService(Context.NOTIFICATION_SERVICE);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(
        ctx).setSmallIcon(android.R.drawable.sym_def_app_icon)
        .setTicker("New msg!").setContentTitle("This is the msg title")
        .setContentText("content...")
        .setContentIntent(getPendingIntent(ctx));

    builder.addAction(android.R.drawable.ic_menu_send,
        ctx.getString(R.string.activity_msg_button_reply),
        getReplyPendingIntent(ctx));

    builder.addAction(android.R.drawable.ic_menu_delete,
        ctx.getString(R.string.activity_msg_button_delete),
        getDeletePendingIntent(ctx));

    mgr.notify(R.id.activity_main_receive_msg, builder.build());
  }

  private static PendingIntent getDeletePendingIntent(Context ctx) {
    Intent intent = new Intent(ctx, MsgService.class);
    intent.setAction(MsgService.MSG_DELETE);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    return PendingIntent.getService(ctx, 0, intent, 0);
  }

  private static PendingIntent getReplyPendingIntent(Context ctx) {
    Intent intent = new Intent(ctx, ReplyActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    return PendingIntent.getActivity(ctx, 0, intent, 0);
  }

  private static PendingIntent getPendingIntent(Context ctx) {
    Intent intent = new Intent(ctx, MsgActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    return PendingIntent.getActivity(ctx, 0, intent, 0);
  }

  public static void dismissMsgNotification(Context ctx) {
    final NotificationManager mgr;
    mgr = (NotificationManager) ctx
        .getSystemService(Context.NOTIFICATION_SERVICE);
    mgr.cancel(R.id.activity_main_receive_msg);
  }

}
