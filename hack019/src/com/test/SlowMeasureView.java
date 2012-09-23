/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.test;

import com.test.util.ThreadUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class SlowMeasureView extends TextView {

  public SlowMeasureView(Context context) {
    super(context);
  }

  public SlowMeasureView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SlowMeasureView(Context context, AttributeSet attrs,
      int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    /*
     * Please remember this is just a hack for the purpose of the demonstration.
     * Do not block the main thread in production code as it may fire an
     * "Application Not Responding" dialog.
     */
    ThreadUtils.sleep();
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
}
