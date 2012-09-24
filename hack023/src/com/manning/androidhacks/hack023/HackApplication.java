/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023;

import com.manning.androidhacks.hack023.authenticator.AuthenticatorActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.Intent;

public class HackApplication extends Application {

  public Account getCurrentAccount() {
    AccountManager accountManager = AccountManager.get(this);
    Account[] accounts = accountManager
        .getAccountsByType(AuthenticatorActivity.PARAM_ACCOUNT_TYPE);

    if (accounts.length > 0) {
      return accounts[0];
    } else {
      Intent intent = new Intent(this, MainActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
      return null;
    }
  }
}
