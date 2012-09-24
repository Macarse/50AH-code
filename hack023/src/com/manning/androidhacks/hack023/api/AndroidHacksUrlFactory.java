/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.api;

public class AndroidHacksUrlFactory {
  private static String URL_BASE = "http://192.168.0.15:8080/service/";
  public static final String LOGIN = "login/";
  public static final String LOGOUT = "logout/";
  public static final String TODO = "todo/";
  public static final String TODO_ADD = "add/%s";
  public static final String TODO_DELETE = "del/%d";

  private static AndroidHacksUrlFactory instance = null;

  private AndroidHacksUrlFactory() {
  }

  public static AndroidHacksUrlFactory getInstance() {
    if (instance == null) {
      instance = new AndroidHacksUrlFactory();
    }

    return instance;
  }

  public String getLoginUrl() {
    return URL_BASE + LOGIN;
  }

  public String getLoginUrlFmt() {
    return getLoginUrl() + "%s/%s";
  }

  public String getLogoutUrl() {
    return URL_BASE + LOGOUT;
  }

  public String getTodoUrl() {
    return URL_BASE + TODO;
  }

  public String getTodoAddUrlFmt() {
    return URL_BASE + TODO + TODO_ADD;
  }

  public String getTodoDeleteUrlFmt() {
    return URL_BASE + TODO + TODO_DELETE;
  }

}
