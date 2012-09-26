/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack039;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }

  public void onLayarClick(View v) {
    if (!ActivityHelper.isLayarAvailable(this)) {
      ActivityHelper.showDownloadDialog(this);
    } else {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_VIEW);
      Uri uri = Uri.parse("layar://teather/?action=refresh");
      intent.setData(uri);
      startActivity(intent);
    }

  }

}
