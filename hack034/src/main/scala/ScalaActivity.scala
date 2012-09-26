/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
 
package com.manning.androidhacks.hack034

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.TextView

class ScalaActivity extends Activity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(new TextView(this) {
      setText("Activity coded in Scala ")
    })
  }
}
