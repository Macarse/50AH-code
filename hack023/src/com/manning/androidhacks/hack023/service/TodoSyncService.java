/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TodoSyncService extends Service {
  private static final Object sSyncAdapterLock = new Object();
  private static TodoSyncAdapter sSyncAdapter = null;

  @Override
  public void onCreate() {
    synchronized (sSyncAdapterLock) {
      if (sSyncAdapter == null) {
        sSyncAdapter = new TodoSyncAdapter(getApplicationContext(),
            true);
      }
    }
  }

  @Override
  public IBinder onBind(Intent intent) {
    return sSyncAdapter.getSyncAdapterBinder();
  }

}
