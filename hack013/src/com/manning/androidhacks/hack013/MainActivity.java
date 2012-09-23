/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack013;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
  private TextView mTextView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mTextView = (TextView) findViewById(R.id.my_text_view);

    mTextView.post(new Runnable() {

      @Override
      public void run() {
        String size = String.format("TextView's width: %d, height: %d",
            mTextView.getWidth(), mTextView.getHeight());
        Toast.makeText(MainActivity.this, size, Toast.LENGTH_SHORT)
            .show();

      }
    });
  }
}
