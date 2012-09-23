/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack022.login;

import junit.framework.Assert;

import org.junit.Test;

import com.manning.androidhacks.hack022.login.Login;

public class LoginTest {

  @Test
  public void LoginShouldReturnTrueWhenCredentialsAreOk() {
    Login login = new Login();
    Assert.assertEquals(true, login.login("admin", "admin"));
  }

  @Test
  public void LoginShouldReturnFalseWhenCredentialsAreNotOk() {
    Login login = new Login();
    Assert.assertEquals(false, login.login("user", "wrongPass"));
  }
}
