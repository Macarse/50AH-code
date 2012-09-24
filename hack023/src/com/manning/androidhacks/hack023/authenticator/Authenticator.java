/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.authenticator;

import com.manning.androidhacks.hack023.api.LoginServiceImpl;
import com.manning.androidhacks.hack023.exception.AndroidHacksException;
import com.manning.androidhacks.hack023.provider.DatabaseHelper;
import com.manning.androidhacks.hack023.provider.TodoContentProvider;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Authenticator extends AbstractAccountAuthenticator {
  public String[] authoritiesToSync = { TodoContentProvider.AUTHORITY };
  private final Context mContext;

  public Authenticator(Context context) {
    super(context);
    mContext = context;
  }

  @Override
  public Bundle addAccount(AccountAuthenticatorResponse response,
      String accountType, String authTokenType,
      String[] requiredFeatures, Bundle options)
      throws NetworkErrorException {

    final Intent intent = new Intent(mContext,
        AuthenticatorActivity.class);
    intent.putExtra(AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE,
        authTokenType);
    intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,
        response);
    final Bundle bundle = new Bundle();
    bundle.putParcelable(AccountManager.KEY_INTENT, intent);

    return bundle;

  }

  @Override
  public Bundle confirmCredentials(
      AccountAuthenticatorResponse response, Account account,
      Bundle options) throws NetworkErrorException {
    return null;
  }

  @Override
  public Bundle editProperties(AccountAuthenticatorResponse response,
      String accountType) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Bundle getAuthToken(AccountAuthenticatorResponse response,
      Account account, String authTokenType, Bundle options)
      throws NetworkErrorException {

    if (!authTokenType
        .equals(AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE)) {

      final Bundle result = new Bundle();
      result.putString(AccountManager.KEY_ERROR_MESSAGE,
          "invalid authTokenType");

      return result;
    }

    final AccountManager am = AccountManager.get(mContext);
    final String password = am.getPassword(account);

    if (password != null) {
      boolean verified = false;

      String loginResponse = null;
      try {
        loginResponse = LoginServiceImpl.sendCredentials(account.name,
            password);
        verified = LoginServiceImpl.hasLoggedIn(loginResponse);
      } catch (AndroidHacksException e) {
        verified = false;
      }

      if (verified) {
        final Bundle result = new Bundle();
        result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
        result.putString(AccountManager.KEY_ACCOUNT_TYPE,
            AuthenticatorActivity.PARAM_ACCOUNT_TYPE);

        return result;
      }
    }
    // Password is missing or incorrect
    final Intent intent = new Intent(mContext,
        AuthenticatorActivity.class);
    intent.putExtra(AuthenticatorActivity.PARAM_USER, account.name);
    intent.putExtra(AuthenticatorActivity.PARAM_AUTHTOKEN_TYPE,
        authTokenType);
    intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,
        response);
    final Bundle bundle = new Bundle();
    bundle.putParcelable(AccountManager.KEY_INTENT, intent);
    return bundle;
  }

  @Override
  public String getAuthTokenLabel(String authTokenType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Bundle hasFeatures(AccountAuthenticatorResponse response,
      Account account, String[] features) throws NetworkErrorException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Bundle updateCredentials(
      AccountAuthenticatorResponse response, Account account,
      String authTokenType, Bundle options)
      throws NetworkErrorException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Bundle getAccountRemovalAllowed(
      AccountAuthenticatorResponse response, Account account)
      throws NetworkErrorException {
    Bundle result = super.getAccountRemovalAllowed(response, account);

    if (result != null
        && result.containsKey(AccountManager.KEY_BOOLEAN_RESULT)
        && !result.containsKey(AccountManager.KEY_INTENT)) {
      boolean allowed = result
          .getBoolean(AccountManager.KEY_BOOLEAN_RESULT);

      if (allowed) {
        for (int i = 0; i < authoritiesToSync.length; i++) {
          ContentResolver.cancelSync(account, authoritiesToSync[i]);
        }

        mContext.deleteDatabase(DatabaseHelper.DATABASE_NAME);
      }
    }

    return result;
  }
}
