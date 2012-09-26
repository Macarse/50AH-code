/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack030;

import java.util.ArrayList;
import java.util.List;

import com.manning.androidhacks.hack030.adapter.CountryAdapter;
import com.manning.androidhacks.hack030.model.Country;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
  private ListView mListView;
  private CountryAdapter mAdapter;
  private List<Country> mCountries;
  private String mToastFmt;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    createCountriesList();
    mToastFmt = getString(R.string.activity_main_toast_fmt);
    mAdapter = new CountryAdapter(this, -1, mCountries);
    mListView = (ListView) findViewById(R.id.activity_main_list);
    mListView.setAdapter(mAdapter);
  }

  public void onPickCountryClick(View v) {
    int pos = mListView.getCheckedItemPosition();

    if (ListView.INVALID_POSITION != pos) {
      String msg = String.format(mToastFmt, mCountries.get(pos)
          .getName());
      Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
  }

  private void createCountriesList() {
    mCountries = new ArrayList<Country>(Countries.COUNTRIES.length);
    for (int i = 0; i < Countries.COUNTRIES.length; i++) {
      Country country = new Country();
      country.setName(Countries.COUNTRIES[i]);
      mCountries.add(country);
    }
  }

}
