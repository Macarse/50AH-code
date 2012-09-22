/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack005;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity {

  private static final String[] TEXTS = { "First", "Second", "Third" };
  private int mTextsPosition = 0;
  private TextSwitcher mTextSwitcher;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mTextSwitcher = (TextSwitcher) findViewById(R.id.your_textview);

    mTextSwitcher.setFactory(new ViewFactory() {
      @Override
      public View makeView() {
        TextView t = new TextView(MainActivity.this);
        t.setGravity(Gravity.CENTER);
        return t;
      }
    });

    mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
    mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);

    onSwitchText(null);
  }

  public void onSwitchText(View v) {
    mTextSwitcher.setText(TEXTS[mTextsPosition]);
    setNextPosition();
  }

  private void setNextPosition() {
    mTextsPosition = (mTextsPosition + 1) % TEXTS.length;
  }
}
