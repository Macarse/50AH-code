/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.test;

import com.test.util.ThreadUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class SlowLayoutView extends TextView {

  public SlowLayoutView(Context context) {
    super(context);
  }

  public SlowLayoutView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SlowLayoutView(Context context, AttributeSet attrs,
      int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top,
      int right, int bottom) {
    /*
     * Please remember this is just a hack for the purpose of the demonstration.
     * Do not block the main thread in production code as it may fire an
     * "Application Not Responding" dialog.
     */
    ThreadUtils.sleep();
    super.onLayout(changed, left, top, right, bottom);
  }
}
