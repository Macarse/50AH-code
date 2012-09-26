/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack030.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.manning.androidhacks.hack030.R;

public class CountryView extends LinearLayout implements Checkable {

  private TextView mTitle;
  private CheckBox mCheckBox;

  public CountryView(Context context) {
    this(context, null);
  }

  public CountryView(Context context, AttributeSet attrs) {
    super(context, attrs);
    LayoutInflater inflater = LayoutInflater.from(context);
    View v = inflater.inflate(R.layout.country_view, this, true);
    mTitle = (TextView) v.findViewById(R.id.country_view_title);
    mCheckBox = (CheckBox) v.findViewById(R.id.country_view_checkbox);
  }

  public void setTitle(String title) {
    mTitle.setText(title);
  }

  @Override
  public boolean isChecked() {
    return mCheckBox.isChecked();
  }

  @Override
  public void setChecked(boolean checked) {
    mCheckBox.setChecked(checked);
  }

  @Override
  public void toggle() {
    mCheckBox.toggle();
  }

}
