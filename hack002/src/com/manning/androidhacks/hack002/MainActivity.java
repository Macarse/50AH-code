/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack002;

import android.os.Bundle;
import android.view.View;
import com.google.android.maps.MapActivity;

public class MainActivity extends MapActivity {

  private View mViewStub;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    mViewStub = findViewById(R.id.map_stub);
  }

  public void onShowMap(View v) {
    mViewStub.setVisibility(View.VISIBLE);
  }

  @Override
  protected boolean isRouteDisplayed() {
    return false;
  }
}
