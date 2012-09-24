/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023;

import android.accounts.Account;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.manning.androidhacks.hack023.adapter.TodoAdapter;
import com.manning.androidhacks.hack023.provider.TodoContentProvider;

public class MainActivity extends Activity {

  private ListView mListView;
  private TodoAdapter mAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    mListView = (ListView) findViewById(R.id.main_activity_listview);
    mAdapter = new TodoAdapter(this);
    mListView.setAdapter(mAdapter);
  }

  public void addNew(View v) {
    startActivity(new Intent(this, AddNewActivity.class));
  }

  public void refresh(View v) {
    Bundle extras = new Bundle();
    extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);

    Account account = ((HackApplication) getApplication())
        .getCurrentAccount();

    if (ContentResolver.isSyncPending(account,
        TodoContentProvider.AUTHORITY)) {
      ContentResolver
          .cancelSync(account, TodoContentProvider.AUTHORITY);
    }

    ContentResolver.requestSync(account, TodoContentProvider.AUTHORITY,
        extras);
  }

}
