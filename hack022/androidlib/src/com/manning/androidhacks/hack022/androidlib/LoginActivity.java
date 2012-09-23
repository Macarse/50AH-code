/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack022.androidlib;

import com.manning.androidhacks.hack022.login.Login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

  private EditText mEmail;
  private EditText mPassword;
  private Button mLoginButton;
  private Login mLoginModel;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);

    mLoginModel = new Login();
    getViews();
  }

  protected void getViews() {
    mEmail = (EditText) findViewById(R.id.login_email);
    mPassword = (EditText) findViewById(R.id.login_password);
    mLoginButton = (Button) findViewById(R.id.login_button);
    mLoginButton.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        onLoginPress();
      }
    });
  }

  protected void onLoginPress() {
    String user = mEmail.getText().toString();
    String passwd = mPassword.getText().toString();

    if (mLoginModel.login(user, passwd)) {
      Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
    }
  }
}
