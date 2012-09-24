/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.authenticator;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.manning.androidhacks.hack023.R;
import com.manning.androidhacks.hack023.net.NetworkUtilities;
import com.manning.androidhacks.hack023.provider.TodoContentProvider;

public class AuthenticatorActivity extends AccountAuthenticatorActivity {
  private static final String TAG = AuthenticatorActivity.class
      .getCanonicalName();
  public static final String PARAM_ACCOUNT_TYPE = "com.manning.androidhacks.hack023";
  public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";

  public static final String PARAM_USER = "user";
  public static final String PARAM_CONFIRMCREDENTIALS = "confirmCredentials";
  private AccountManager mAccountManager;
  private Thread mAuthThread;
  private String mAuthToken;
  private String mAuthTokenType;
  private Boolean mConfirmCredentials = false;
  private final Handler mHandler = new Handler();

  private TextView mMessage;
  private String mPassword;
  private EditText mPasswordEdit;
  private String mUsername;
  private EditText mUsernameEdit;
  private Button mSignInButton;

  /** Was the original caller asking for an entirely new account? */
  protected boolean mRequestNewAccount = false;
  private String mUser;

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);

    mAccountManager = AccountManager.get(this);
    checkMaximumNumberOfAccounts();

    final Intent intent = getIntent();

    mUser = intent.getStringExtra(PARAM_USER);
    mAuthTokenType = intent.getStringExtra(PARAM_AUTHTOKEN_TYPE);
    mRequestNewAccount = mUsername == null;
    mConfirmCredentials = intent.getBooleanExtra(
        PARAM_CONFIRMCREDENTIALS, false);

    Log.i(TAG, "    request new: " + mRequestNewAccount);
    requestWindowFeature(Window.FEATURE_LEFT_ICON);
    setContentView(R.layout.login);
    getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
        android.R.drawable.ic_dialog_alert);

    findViews();
    initFields();
  }

  private void initFields() {
    mUsernameEdit.setText(mUser);
    mMessage.setText(getMessage());

    mSignInButton.setOnClickListener(new OnClickListener() {

      public void onClick(View view) {
        handleLogin(view);
      }

    });
  }

  private void handleLogin(View view) {
    if (mRequestNewAccount) {
      mUsername = mUsernameEdit.getText().toString();
    }

    mPassword = mPasswordEdit.getText().toString();

    if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
      mMessage.setText(getMessage());
    }

    showProgress();
    mAuthThread = NetworkUtilities.attemptAuth(mUsername, mPassword,
        mHandler, AuthenticatorActivity.this);
  }

  private void showProgress() {
    showDialog(0);
  }

  private void hideProgress() {
    dismissDialog(0);
  }

  public void onAuthenticationResult(Boolean result) {
    hideProgress();

    if (result) {
      if (!mConfirmCredentials) {
        finishLogin();
      } else {
        // TODO see if we need to confirm credentials
      }
    } else {
      Log.e(TAG, "onAuthenticationResult: failed to authenticate");
      mMessage.setText("User and/or password are incorrect");
    }
  }

  @Override
  protected Dialog onCreateDialog(int id) {
    final ProgressDialog dialog = new ProgressDialog(this);
    dialog.setMessage("Login in");
    dialog.setIndeterminate(true);
    dialog.setCancelable(true);
    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
      public void onCancel(DialogInterface dialog) {
        if (mAuthThread != null) {
          mAuthThread.interrupt();
          finish();
        }
      }
    });
    return dialog;
  }

  private void finishLogin() {
    final Account account = new Account(mUsername, PARAM_ACCOUNT_TYPE);

    if (mRequestNewAccount) {
      mAccountManager.addAccountExplicitly(account, mPassword, null);

      Bundle bundle = new Bundle();
      ContentResolver.addPeriodicSync(account,
          TodoContentProvider.AUTHORITY, bundle, 300);

    } else {
      mAccountManager.setPassword(account, mPassword);
    }

    final Intent intent = new Intent();
    intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, mUsername);
    intent
        .putExtra(AccountManager.KEY_ACCOUNT_TYPE, PARAM_ACCOUNT_TYPE);

    if (mAuthTokenType != null
        && mAuthTokenType.equals(PARAM_AUTHTOKEN_TYPE)) {
      intent.putExtra(AccountManager.KEY_AUTHTOKEN, mAuthToken);
    }

    setAccountAuthenticatorResult(intent.getExtras());
    setResult(RESULT_OK, intent);
    finish();
  }

  private void findViews() {
    mMessage = (TextView) findViewById(R.id.message);
    mUsernameEdit = (EditText) findViewById(R.id.fid_edit);
    mPasswordEdit = (EditText) findViewById(R.id.password_edit);
    mSignInButton = (Button) findViewById(R.id.ok_button);
  }

  private CharSequence getMessage() {

    if (TextUtils.isEmpty(mUsername)) {
      return "New account";
    }

    if (TextUtils.isEmpty(mPassword)) {
      return "Password is missing";
    }

    return null;
  }

  private void checkMaximumNumberOfAccounts() {
    Account[] accounts = mAccountManager
        .getAccountsByType(PARAM_ACCOUNT_TYPE);

    if (accounts.length != 0) {
      Toast.makeText(this, "More than one account", Toast.LENGTH_SHORT)
          .show();
      finish();
    }

  }
}
