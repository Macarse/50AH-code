package com.manning.androidhacks.hack028;

import com.manning.androidhacks.hack028.adapter.ImageAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ListView;

public class MainActivity extends Activity {

  private static final String[] NUMBERS = { "1", "2", "3", "4", "5",
      "6", "7", "8" };
  private Gallery mGallery;
  private View mHeader;
  private ListView mListView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mListView = (ListView) findViewById(R.id.main_listview);

    LayoutInflater inflator = LayoutInflater.from(this);
    mHeader = inflator.inflate(R.layout.header, mListView, false);
    mGallery = (Gallery) mHeader
        .findViewById(R.id.listview_header_gallery);
    mGallery.setAdapter(new ImageAdapter(this));

    ListView.LayoutParams params = new ListView.LayoutParams(
        ListView.LayoutParams.FILL_PARENT,
        ListView.LayoutParams.WRAP_CONTENT);
    mHeader.setLayoutParams(params);
    mListView.addHeaderView(mHeader, null, false);

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        R.layout.list_item, NUMBERS);
    mListView.setAdapter(adapter);

    mListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view,
          int position, long id) {
        mGallery.setSelection(position - 1, true);
      }
    });
  }
}