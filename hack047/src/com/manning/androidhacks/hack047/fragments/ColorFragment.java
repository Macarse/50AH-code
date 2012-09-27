/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack047.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manning.androidhacks.hack047.R;

public class ColorFragment extends Fragment {

  private static final String COLOR_KEY = "color_key";
  private static final String TEXT_KEY = "text_key";

  private int mColor;
  private String mText;

  public static ColorFragment newInstance(int color, String text) {
    final ColorFragment fragment = new ColorFragment();

    Bundle args = new Bundle();
    args.putInt(COLOR_KEY, color);
    args.putString(TEXT_KEY, text);

    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final Bundle bundle = getArguments();
    if (bundle != null) {
      mColor = bundle.getInt(COLOR_KEY);
      mText = bundle.getString(TEXT_KEY);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {

    if (container == null) {
      return null;
    }

    final TextView textView = (TextView) inflater.inflate(
        R.layout.color_fragment, container, false);

    textView.setBackgroundColor(mColor);
    textView.setText(mText);

    return textView;
  }
}
