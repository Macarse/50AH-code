/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack029;

import com.manning.androidhacks.hack029.adapter.ColorAdapter;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class MainActivity extends FragmentActivity {

  private ViewPager mViewPager;
  private ColorAdapter mAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    setContentView(R.layout.main);

    mViewPager = (ViewPager) findViewById(R.id.pager);
    mAdapter = new ColorAdapter(getSupportFragmentManager());
    mViewPager.setAdapter(mAdapter);

    mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

      @Override
      public void onPageSelected(int position) {
        if (mAdapter.usesLandscape(position)) {
          allowOrientationChanges();
        } else {
          enforcePortrait();
        }

      }

      @Override
      public void onPageScrolled(int arg0, float arg1, int arg2) {
      }

      @Override
      public void onPageScrollStateChanged(int arg0) {
      }
    });
  }

  public void allowOrientationChanges() {
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
  }

  public void enforcePortrait() {
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }
}
