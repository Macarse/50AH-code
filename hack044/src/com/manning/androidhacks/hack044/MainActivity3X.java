/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack044;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnSystemUiVisibilityChangeListener;

public class MainActivity3X extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    final View contentView = findViewById(R.id.content);

    contentView
        .setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener() {
          @Override
          public void onSystemUiVisibilityChange(int visibility) {
            final ActionBar actionBar = getActionBar();
            if (actionBar != null) {
              contentView.setSystemUiVisibility(visibility);

              if (visibility == View.STATUS_BAR_VISIBLE) {
                actionBar.show();
              } else {
                actionBar.hide();
              }
            }
          }
        });

    contentView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (contentView.getSystemUiVisibility() == View.STATUS_BAR_VISIBLE) {
          contentView.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
        } else {
          contentView.setSystemUiVisibility(View.STATUS_BAR_VISIBLE);
        }
      }
    });
  }

}
