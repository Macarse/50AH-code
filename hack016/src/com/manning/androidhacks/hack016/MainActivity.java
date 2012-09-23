/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack016;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void upperLeft(View v) {
    Toast toast = Toast.makeText(this, "Upper Left!",
        Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
    toast.show();
  }

  public void upperRight(View v) {
    Toast toast = Toast.makeText(this, "Upper Right!",
        Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.TOP | Gravity.RIGHT, 0, 0);
    toast.show();
  }

  public void bottomLeft(View v) {
    Toast toast = Toast.makeText(this, "Bottom Left!",
        Toast.LENGTH_SHORT);

    toast.setGravity(Gravity.BOTTOM | Gravity.LEFT, 0, 0);
    toast.show();
  }

  public void bottomRight(View v) {
    Toast toast = Toast.makeText(this, "Bottom Right!",
        Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
    toast.show();
  }
}
