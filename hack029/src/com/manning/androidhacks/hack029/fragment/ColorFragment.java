/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack029.fragment;

import com.manning.androidhacks.hack029.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ColorFragment extends Fragment {

  private static final String COLOR_KEY = "color_key";
  private static final String TEXT_KEY = "text_key";
  private static final String LANDSCAPE_KEY = "landscape_key";

  private int mColor = Color.BLACK;
  private String mText = "Default text";
  private boolean mIsLandscape = false;

  public static ColorFragment newInstance(int color, String text,
      boolean isLandscape) {
    ColorFragment recreateFragment = new ColorFragment();

    Bundle args = new Bundle();
    args.putInt(COLOR_KEY, color);
    args.putString(TEXT_KEY, text);
    args.putBoolean(LANDSCAPE_KEY, isLandscape);

    recreateFragment.setArguments(args);

    return recreateFragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle bundle = getArguments();
    if (getArguments() != null) {
      mColor = bundle.getInt(COLOR_KEY);
      mText = bundle.getString(TEXT_KEY);
      mIsLandscape = bundle.getBoolean(LANDSCAPE_KEY);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {

    if (container == null) {
      return null;
    }

    final View rootView = inflater.inflate(R.layout.color_fragment,
        container, false);
    final TextView textView = (TextView) rootView
        .findViewById(R.id.color_fragment_text);

    rootView.setBackgroundColor(mColor);
    textView.setText(mText);

    return rootView;
  }
}
