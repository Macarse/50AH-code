/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack047;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.manning.androidhacks.hack047.fragments.ColorFragment;

public class MainActivity extends FragmentActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    findViewById(R.id.tab_red).setOnClickListener(
        new OnClickListener() {
          @Override
          public void onClick(View v) {
            switchToFragment(ColorFragment.newInstance(Color.RED,
                getString(R.string.red)));
          }
        });

    findViewById(R.id.tab_green).setOnClickListener(
        new OnClickListener() {
          @Override
          public void onClick(View v) {
            switchToFragment(ColorFragment.newInstance(Color.GREEN,
                getString(R.string.green)));
          }
        });

    findViewById(R.id.tab_blue).setOnClickListener(
        new OnClickListener() {
          @Override
          public void onClick(View v) {
            switchToFragment(ColorFragment.newInstance(Color.BLUE,
                getString(R.string.blue)));
          }
        });
  }

  private void switchToFragment(Fragment fragment) {
    FragmentTransaction ft = getSupportFragmentManager()
        .beginTransaction();
    ft.replace(R.id.main_fragment_container, fragment);
    ft.commit();
  }
}
