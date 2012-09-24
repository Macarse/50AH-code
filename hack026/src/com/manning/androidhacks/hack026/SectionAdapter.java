/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack026;

import com.manning.androidhacks.hack026.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SectionAdapter extends ArrayAdapter<String> {

  private Activity activity;

  public SectionAdapter(Activity activity, String[] objects) {
    super(activity, R.layout.list_item, R.id.label, objects);
    this.activity = activity;
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {
    if (view == null) {
      view = activity.getLayoutInflater().inflate(R.layout.list_item,
          parent, false);
    }
    TextView header = (TextView) view.findViewById(R.id.header);
    String label = getItem(position);
    if (position == 0
        || getItem(position - 1).charAt(0) != label.charAt(0)) {
      header.setVisibility(View.VISIBLE);
      header.setText(label.substring(0, 1));
    } else {
      header.setVisibility(View.GONE);
    }
    return super.getView(position, view, parent);
  }
}
