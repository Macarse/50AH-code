/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack042.adapter;

import java.util.List;

import com.manning.androidhacks.hack042.R;
import com.manning.androidhacks.hack042.model.Poi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PoisAdapter extends ArrayAdapter<Poi> {
  private LayoutInflater mInflater;

  public PoisAdapter(Context context, int textViewResourceId,
      List<Poi> objects) {
    super(context, textViewResourceId, objects);
    mInflater = LayoutInflater.from(context);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    TextView tv;

    if (convertView == null) {
      convertView = createView();
    }

    Poi poi = getItem(position);

    tv = (TextView) convertView.findViewById(R.id.poi_row_title);
    tv.setText(poi.getName());
    tv = (TextView) convertView.findViewById(R.id.poi_row_latitude);
    tv.setText("" + poi.getLatitude());
    tv = (TextView) convertView.findViewById(R.id.poi_row_longitude);
    tv.setText("" + poi.getLongitude());
    tv = (TextView) convertView.findViewById(R.id.poi_row_distance);
    tv.setText("" + poi.getDistance());

    return convertView;
  }

  private View createView() {
    return mInflater.inflate(R.layout.poi_row, null);
  }
}
