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

import com.manning.androidhacks.hack043.adapter.BatchAdapter;
import com.manning.androidhacks.hack043.provider.BatchNumbersContentProvider;
import com.manning.androidhacks.hack043.service.BatchService;

public class BatchActivity extends Activity {
  private static final String[] PROJECTION = new String[] {
      BatchNumbersContentProvider.COLUMN_ID,
      BatchNumbersContentProvider.COLUMN_TEXT };

  private ListView mListView;
  private BatchAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list);
    updateList();

    mListView = (ListView) findViewById(R.id.list_listview);
    Cursor cursor = getCursor();
    mAdapter = new BatchAdapter(this, cursor);
    mListView.setAdapter(mAdapter);
  }

  private Cursor getCursor() {
    return managedQuery(BatchNumbersContentProvider.CONTENT_URI,
        PROJECTION, null, null,
        BatchNumbersContentProvider.DEFAULT_SORT_ORDER);
  }

  private void updateList() {
    startService(new Intent(this, BatchService.class));
  }

  public void onUpdateClick(View v) {
    updateList();
  }
}
