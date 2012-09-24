/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack025;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ModelAdapter extends ArrayAdapter<Model> {

  private LayoutInflater mInflater;

  public ModelAdapter(Context context, int textViewResourceId,
      List<Model> objects) {
    super(context, textViewResourceId, objects);
    mInflater = LayoutInflater.from(context);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final ViewHolder viewHolder;

    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.row_layout, parent,
          false);

      viewHolder = new ViewHolder();
      viewHolder.imageView = (ImageView) convertView
          .findViewById(R.id.image);
      viewHolder.text1 = (TextView) convertView
          .findViewById(R.id.text1);
      viewHolder.text2 = (TextView) convertView
          .findViewById(R.id.text2);

      convertView.setTag(viewHolder);

    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    Model model = getItem(position);
    viewHolder.imageView.setImageResource(model.getImage());
    viewHolder.text1.setText(model.getText1());
    viewHolder.text2.setText(model.getText2());

    return convertView;
  }

  private static class ViewHolder {
    public ImageView imageView;
    public TextView text1;
    public TextView text2;
  }
}
