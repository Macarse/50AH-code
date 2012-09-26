/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack037;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
  private static final String FOLDER_PATH = Environment
      .getExternalStorageDirectory().getPath() + "/60AH/hack037/";
  private static final String LOOP1_PATH = FOLDER_PATH + "loop1.mp3";
  private static final String LOOP2_PATH = FOLDER_PATH + "loop2.mp3";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void onLoop1Click(View v) {
    if (!canWriteInExternalStorage()) {
      Toast.makeText(this, "Can't write file", Toast.LENGTH_SHORT)
          .show();
      return;
    }

    Log.d("TAG", "LOOP1: " + LOOP1_PATH);
    MediaUtils.saveRaw(this, R.raw.loop1, LOOP1_PATH);

    ContentValues values = new ContentValues(5);
    values.put(Media.ARTIST, "Android");
    values.put(Media.ALBUM, "60AH");
    values.put(Media.TITLE, "hack043");
    values.put(Media.MIME_TYPE, "audio/mp3");
    values.put(Media.DATA, LOOP1_PATH);

    getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
  }

  public void onLoop2Click(View v) {
    if (!canWriteInExternalStorage()) {
      Toast.makeText(this, "Can't write file", Toast.LENGTH_SHORT)
          .show();
      return;
    }

    MediaUtils.saveRaw(this, R.raw.loop2, LOOP2_PATH);

    Uri uri = Uri.parse("file://" + LOOP2_PATH);
    Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
    sendBroadcast(i);
  }

  private boolean canWriteInExternalStorage() {
    boolean mExternalStorageAvailable = false;
    boolean mExternalStorageWriteable = false;
    String state = Environment.getExternalStorageState();

    if (Environment.MEDIA_MOUNTED.equals(state)) {
      // We can read and write the media
      mExternalStorageAvailable = mExternalStorageWriteable = true;
    } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
      // We can only read the media
      mExternalStorageAvailable = true;
      mExternalStorageWriteable = false;
    } else {
      // Something else is wrong. It may be one of many other states,
      // but all we need to know is we can neither read nor write
      mExternalStorageAvailable = mExternalStorageWriteable = false;
    }

    return mExternalStorageAvailable && mExternalStorageWriteable;
  }
}
