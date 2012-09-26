/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack043.service;

import java.util.ArrayList;
import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.util.Log;

import com.manning.androidhacks.hack043.provider.MySQLContentProvider;

public class SQLContentProviderService extends IntentService {
  private static final String TAG = SQLContentProviderService.class
      .getCanonicalName();

  public SQLContentProviderService() {
    super(SQLContentProviderService.class.getSimpleName());
  }

  public SQLContentProviderService(String name) {
    super(name);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Builder builder = null;
    ContentResolver contentResolver = getContentResolver();
    ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();

    builder = ContentProviderOperation
        .newDelete(MySQLContentProvider.CONTENT_URI);
    operations.add(builder.build());

    for (int i = 1; i <= 100; i++) {
      ContentValues cv = new ContentValues();
      cv.put(MySQLContentProvider.COLUMN_TEXT, "" + i);

      builder = ContentProviderOperation
          .newInsert(MySQLContentProvider.CONTENT_URI);
      builder.withValues(cv);

      operations.add(builder.build());
    }

    try {
      contentResolver.applyBatch(MySQLContentProvider.AUTHORITY,
          operations);
    } catch (RemoteException e) {
      Log.e(TAG, "Couldn't apply batch: " + e.getMessage());
    } catch (OperationApplicationException e) {
      Log.e(TAG, "Couldn't apply batch: " + e.getMessage());
    }

  }

}
