/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack043.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.manning.androidhacks.hack043.R;
import com.manning.androidhacks.hack043.provider.MySQLContentProvider;

public class SQLContentProviderAdapter extends CursorAdapter {
  private LayoutInflater mInflater;
  private final int mIdIndex;
  private final int mNumberIndex;

  public SQLContentProviderAdapter(Context context, Cursor c) {
    super(context, c, true);
    mInflater = LayoutInflater.from(context);

    mIdIndex = c.getColumnIndexOrThrow(MySQLContentProvider.COLUMN_ID);
    mNumberIndex = c
        .getColumnIndexOrThrow(MySQLContentProvider.COLUMN_TEXT);
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    View view = mInflater.inflate(R.layout.list_item, parent, false);

    ViewHolder holder = new ViewHolder();
    holder.id = (TextView) view.findViewById(R.id.list_item_id);
    holder.number = (TextView) view.findViewById(R.id.list_item_number);
    view.setTag(holder);

    return view;
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    ViewHolder holder = (ViewHolder) view.getTag();
    holder.id.setText(cursor.getString(mIdIndex));
    holder.number.setText(cursor.getString(mNumberIndex));
  }

  private static final class ViewHolder {
    public TextView id;
    public TextView number;
  }
}
