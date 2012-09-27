/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack044;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity2X extends Activity {

  private boolean mUseFullscreen = true;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_NO_TITLE);

    setContentView(R.layout.main);
    findViewById(R.id.content).setOnClickListener(
        new OnClickListener() {
          @Override
          public void onClick(View v) {

            if (mUseFullscreen) {
              getWindow().addFlags(
                  WindowManager.LayoutParams.FLAG_FULLSCREEN);
              getWindow().clearFlags(
                  WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            } else {
              getWindow().addFlags(
                  WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
              getWindow().clearFlags(
                  WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }

            mUseFullscreen = !mUseFullscreen;
          }
        });
  }
}
