/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack020.presenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.manning.androidhacks.hack020.presenter.SplashPresenter;
import com.manning.androidhacks.hack020.presenter.model.IConnectionStatus;
import com.manning.androidhacks.hack020.view.ISplashView;

public class SplashTest {

  @Test
  public void testViewShouldShowNoInternetMsgWhenThereIsNoInternet() {
    IConnectionStatus mockedConnectionStatus = mock(IConnectionStatus.class);
    when(mockedConnectionStatus.isOnline()).thenReturn(false);

    ISplashView mockedSplashView = mock(ISplashView.class);

    SplashPresenter splashPresenter = new SplashPresenter(
        mockedConnectionStatus);
    splashPresenter.setView(mockedSplashView);

    splashPresenter.didFinishLoading();

    verify(mockedSplashView).hideProgress();
    verify(mockedSplashView).showNoInetErrorMsg();
  }

  @Test
  public void testViewShouldMoveTomoveToMainViewWhenInternetIsOn() {
    IConnectionStatus mockedConnectionStatus = mock(IConnectionStatus.class);
    when(mockedConnectionStatus.isOnline()).thenReturn(true);

    ISplashView mockedSplashView = mock(ISplashView.class);

    SplashPresenter splashPresenter = new SplashPresenter(
        mockedConnectionStatus);
    splashPresenter.setView(mockedSplashView);

    splashPresenter.didFinishLoading();

    verify(mockedSplashView).moveToMainView();
  }
}
