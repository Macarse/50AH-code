/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack030.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.manning.androidhacks.hack030.model.Country;
import com.manning.androidhacks.hack030.view.CountryView;

public class CountryAdapter extends ArrayAdapter<Country> {

  public CountryAdapter(Context context, int textViewResourceId,
      List<Country> objects) {
    super(context, textViewResourceId, objects);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if ( convertView == null ) {
      convertView = new CountryView(getContext());
    }

    Country country = getItem(position);

    CountryView countryView = (CountryView) convertView;
    countryView.setTitle(country.getName());

    return convertView;
  }
}
