/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack020.presenter;

import com.manning.androidhacks.hack020.presenter.model.IConnectionStatus;
import com.manning.androidhacks.hack020.presenter.model.impl.ConnectionStatus;
import com.manning.androidhacks.hack020.view.ISplashView;

public class SplashPresenter {

  private IConnectionStatus mConnectionStatus;
  private ISplashView mView;

  public SplashPresenter() {
    this(new ConnectionStatus());
  }

  public SplashPresenter(IConnectionStatus connectionStatus) {
    mConnectionStatus = connectionStatus;
  }

  public void setView(ISplashView view) {
    this.mView = view;
  }

  protected ISplashView getView() {
    return mView;
  }

  public void didFinishLoading() {
    ISplashView view = getView();

    if (mConnectionStatus.isOnline()) {
      view.moveToMainView();
    } else {
      view.hideProgress();
      view.showNoInetErrorMsg();
    }
  }
}
