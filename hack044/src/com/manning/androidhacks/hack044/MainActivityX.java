/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack044;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class MainActivityX extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Class<?> activity = null;
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
      activity = MainActivity2X.class;
    } else {
      activity = MainActivity3X.class;
    }

    startActivity(new Intent(this, activity));
    finish();

  }

}
