/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack043;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.manning.androidhacks.hack043.adapter.NoBatchAdapter;
import com.manning.androidhacks.hack043.provider.NoBatchNumbersContentProvider;
import com.manning.androidhacks.hack043.service.NoBatchService;

public class NoBatchActivity extends Activity {
  private static final String[] PROJECTION = new String[] {
      NoBatchNumbersContentProvider.COLUMN_ID,
      NoBatchNumbersContentProvider.COLUMN_TEXT };

  private ListView mListView;
  private NoBatchAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list);
    updateList();

    mListView = (ListView) findViewById(R.id.list_listview);
    Cursor cursor = getCursor();
    mAdapter = new NoBatchAdapter(this, cursor);
    mListView.setAdapter(mAdapter);
  }

  private Cursor getCursor() {
    return managedQuery(NoBatchNumbersContentProvider.CONTENT_URI,
        PROJECTION, null, null,
        NoBatchNumbersContentProvider.DEFAULT_SORT_ORDER);
  }

  private void updateList() {
    startService(new Intent(this, NoBatchService.class));
  }

  public void onUpdateClick(View v) {
    updateList();
  }
}
