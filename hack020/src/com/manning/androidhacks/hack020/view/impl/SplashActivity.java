/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack020.view.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.manning.androidhacks.hack020.R;
import com.manning.androidhacks.hack020.presenter.SplashPresenter;
import com.manning.androidhacks.hack020.view.ISplashView;

public class SplashActivity extends Activity implements ISplashView {

  private TextView mTextView;
  private ProgressBar mProgressBar;
  private SplashPresenter mPresenter = new SplashPresenter();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash);

    mPresenter.setView(this);

    mTextView = (TextView) findViewById(R.id.splash_text);
    mProgressBar = (ProgressBar) findViewById(R.id.splash_progress_bar);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mPresenter.didFinishLoading();
  }

  public void showProgress() {
    mProgressBar.setVisibility(View.VISIBLE);
  }

  public void hideProgress() {
    mProgressBar.setVisibility(View.INVISIBLE);
  }

  public void showNoInetErrorMsg() {
    mTextView.setText("No internet");
  }

  @Override
  public void moveToMainView() {
    startActivity(new Intent(this, MainActivity.class));
  }
}
