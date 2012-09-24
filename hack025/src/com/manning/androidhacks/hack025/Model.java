/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack025;

public class Model {

  private String mText1;
  private String mText2;
  private int mImageResId;

  public String getText1() {
    return mText1;
  }

  public void setText1(String text1) {
    mText1 = text1;
  }

  public String getText2() {
    return mText2;
  }

  public void setText2(String text2) {
    mText2 = text2;
  }

  public int getImage() {
    return mImageResId;
  }

  public void setImage(int imageResId) {
    mImageResId = imageResId;
  }
}
