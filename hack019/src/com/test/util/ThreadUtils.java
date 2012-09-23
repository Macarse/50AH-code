/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.test.util;

public class ThreadUtils {

  private static final long DEFAULT_SLEEP_DURATION = 2500L;

  private ThreadUtils() {
  }

  public static void sleep() {
    sleep(DEFAULT_SLEEP_DURATION);
  }

  public static void sleep(long duration) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
