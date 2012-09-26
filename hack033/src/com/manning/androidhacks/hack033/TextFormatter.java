/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack033;

public class TextFormatter {
  public static native String formatString(String text);

  static {
    System.loadLibrary("macemu");
    System.loadLibrary("objc");
    System.loadLibrary("cf");
    System.loadLibrary("foundation");
    System.loadLibrary("textformatter");
    System.loadLibrary("main");
  }
}
