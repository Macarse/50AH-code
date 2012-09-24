/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.manning.androidhacks.hack023.authenticator.AuthenticatorActivity;

public class BootstrapActivity extends Activity {
  private static final String TAG = BootstrapActivity.class
      .getCanonicalName();
  private static final int NEW_ACCOUNT = 0;
  private static final int EXISTING_ACCOUNT = 1;
  private AccountManager mAccountManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.bootstrap);

    mAccountManager = AccountManager.get(this);
    Account[] accounts = mAccountManager
        .getAccountsByType(AuthenticatorActivity.PARAM_ACCOUNT_TYPE);

    if (accounts.length == 0) {
      // There are no androidHacks accounts! We need to create one.
      Log.d(TAG, "No accounts found. Starting login...");
      final Intent intent = new Intent(this,
          AuthenticatorActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
      startActivityForResult(intent, NEW_ACCOUNT);
    } else {
      // For now we assume that there's only one account.
      String password = mAccountManager.getPassword(accounts[0]);
      Log.d(TAG, "Using account with name " + accounts[0].name);
      if (password == null) {
        Log.d(TAG, "The password is empty, launching login");
        final Intent intent = new Intent(this,
            AuthenticatorActivity.class);
        intent.putExtra(AuthenticatorActivity.PARAM_USER,
            accounts[0].name);
        startActivityForResult(intent, EXISTING_ACCOUNT);
      } else {
        Log.d(TAG, "User and password found, no need for manual login");
        // The user is already logged in. Go ahead!
        startActivity(new Intent(this, MainActivity.class));
        finish();
      }
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode,
      Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (mAccountManager
        .getAccountsByType(AuthenticatorActivity.PARAM_ACCOUNT_TYPE).length > 0) {
      Intent i = new Intent(this, MainActivity.class);
      startActivity(i);
      finish();
    } else {
      finish();
    }
  }
}
