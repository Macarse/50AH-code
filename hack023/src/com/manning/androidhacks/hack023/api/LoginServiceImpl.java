/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.api;

import android.util.Log;

import com.manning.androidhacks.hack023.exception.AndroidHacksException;
import com.manning.androidhacks.hack023.net.HttpHelper;

public class LoginServiceImpl {

  private static final String TAG = LoginServiceImpl.class.getCanonicalName();

  public static boolean login(String username, String password)
      throws AndroidHacksException {
    String response = sendCredentials(username, password);
    return hasLoggedIn(response);
  }

  public static String sendCredentials(String username,
      String password) throws AndroidHacksException {
    String fmt = AndroidHacksUrlFactory.getInstance().getLoginUrlFmt();
    String url = String.format(fmt, username, password);
    String ret = HttpHelper.getHttpResponseAsString(url, null);

    return ret;
  }

  public static boolean hasLoggedIn(String response) {
    Log.d(TAG, "response: " + response);
    return  "{\"result\": \"ok\"}".equals(response);
  }

  public boolean logout() {
    return false;
  }
}
