/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.net;

import android.content.Context;
import android.os.Handler;

import com.manning.androidhacks.hack023.api.LoginServiceImpl;
import com.manning.androidhacks.hack023.authenticator.AuthenticatorActivity;
import com.manning.androidhacks.hack023.exception.AndroidHacksException;

public class NetworkUtilities {

  public static Thread performOnBackgroundThread(final Runnable runnable) {
    final Thread t = new Thread() {
      @Override
      public void run() {
        try {
          runnable.run();
        } finally {

        }
      }
    };
    t.start();
    return t;
  }

  public static Thread attemptAuth(final String username,
      final String password, final Handler handler,
      final Context context) {
    final Runnable runnable = new Runnable() {
      public void run() {
        authenticate(username, password, handler, context);
      }

    };
    return NetworkUtilities.performOnBackgroundThread(runnable);
  }

  private static void authenticate(String username, String password,
      Handler handler, Context context) {
    boolean hasLoggedIn = false;

    try {
      String response = LoginServiceImpl.sendCredentials(username,
          password);
      hasLoggedIn = LoginServiceImpl.hasLoggedIn(response);

      if (hasLoggedIn) {
        sendResult(true, handler, context);
      } else {
        sendResult(false, handler, context);
      }
    } catch (AndroidHacksException e) {
      sendResult(false, handler, context);
    }
  }

  private static void sendResult(final Boolean result,
      final Handler handler, final Context context) {
    if (handler == null || context == null) {
      return;
    }
    handler.post(new Runnable() {
      public void run() {
        ((AuthenticatorActivity) context)
            .onAuthenticationResult(result);
      }
    });
  }
}
