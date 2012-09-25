/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack029.adapter;

import com.manning.androidhacks.hack029.fragment.ColorFragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ColorAdapter extends FragmentStatePagerAdapter {
  public static final int[] COLORS = { Color.BLACK, Color.BLUE,
      Color.CYAN, Color.GREEN, Color.RED, Color.WHITE, Color.MAGENTA };

  public ColorAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {

    return ColorFragment.newInstance(COLORS[position], "" + position,
        (position % 2) == 0);
  }

  @Override
  public int getCount() {
    return COLORS.length;
  }

  public boolean usesLandscape(int position) {
    return (position % 2) == 0;
  }
}
