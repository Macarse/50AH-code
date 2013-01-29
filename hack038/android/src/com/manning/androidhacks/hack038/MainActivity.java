/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack038;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockActivity {
  private static final int MENU_REFRESH = 10;
  private MenuItem mRefreshMenu;
  private Button mButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mButton = (Button) findViewById(R.id.activity_main_button);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    mRefreshMenu = menu.add(MENU_REFRESH, MENU_REFRESH, MENU_REFRESH,
        "Refresh");
    mRefreshMenu.setIcon(R.drawable.menu_reload);
    mRefreshMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case MENU_REFRESH:
      handleRefresh(null);
      return true;

    default:
      return super.onOptionsItemSelected(item);
    }
  }

  private void startLoading() {
    mRefreshMenu.setActionView(R.layout.menu_item_refresh);
    mButton.setEnabled(false);
  }

  private void stopLoading() {
    mRefreshMenu.setActionView(null);
    mButton.setEnabled(true);
  }

  public void handleRefresh(View v) {
    new LoadingAsyncTask().execute();
  }

  private class LoadingAsyncTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      startLoading();
    }

    @Override
    protected Void doInBackground(Void... params) {
      SystemClock.sleep(5000L);
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      stopLoading();
    }
  }
}
