/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack007;

import com.manning.androidhacks.hack007.view.DrawView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends Activity {

  private DrawView mDrawView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Display display = getWindowManager().getDefaultDisplay();
    mDrawView = new DrawView(this);
    mDrawView.height = display.getHeight();
    mDrawView.width = display.getWidth();

    setContentView(mDrawView);
  }
}
