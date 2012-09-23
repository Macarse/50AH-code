/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack020.presenter.model.impl;

import com.manning.androidhacks.hack020.presenter.model.IConnectionStatus;

public class ConnectionStatus implements IConnectionStatus {

  @Override
  public boolean isOnline() {
    // TODO: Here we should place the code to check the connectivity.
    return true;
  }

}
