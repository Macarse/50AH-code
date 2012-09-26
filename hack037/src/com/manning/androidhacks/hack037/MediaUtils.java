/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack037;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public final class MediaUtils {

  private static final String TAG = MediaUtils.class.getCanonicalName();

  private MediaUtils() {
  }

  public static void saveRaw(Context context, int raw, String path) {
    File completePath = new File(
        Environment.getExternalStorageDirectory(), path);

    try {
      completePath.getParentFile().mkdirs();
      completePath.createNewFile();

      BufferedOutputStream bos = new BufferedOutputStream(
          (new FileOutputStream(completePath)));
      BufferedInputStream bis = new BufferedInputStream(context
          .getResources().openRawResource(raw));
      byte[] buff = new byte[32 * 1024];
      int len;
      while ((len = bis.read(buff)) > 0) {
        bos.write(buff, 0, len);
      }
      bos.flush();
      bos.close();

    } catch (IOException io) {
      Log.e(TAG, "Error: " + io);
    }
  }
}
