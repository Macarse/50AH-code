/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack043.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;

import com.manning.androidhacks.hack043.provider.NoBatchNumbersContentProvider;

public class NoBatchService extends IntentService {

  public NoBatchService() {
    super(NoBatchService.class.getSimpleName());
  }

  public NoBatchService(String name) {
    super(name);
  }

  @Override
  protected void onHandleIntent(Intent intent) {

    ContentResolver contentResolver = getContentResolver();
    contentResolver.delete(NoBatchNumbersContentProvider.CONTENT_URI,
        null, null);

    for (int i = 1; i <= 100; i++) {
      ContentValues cv = new ContentValues();
      cv.put(NoBatchNumbersContentProvider.COLUMN_TEXT, "" + i);
      contentResolver.insert(NoBatchNumbersContentProvider.CONTENT_URI,
          cv);
    }
  }

}
