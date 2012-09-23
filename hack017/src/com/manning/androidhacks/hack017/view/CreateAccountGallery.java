/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack017.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class CreateAccountGallery extends Gallery {

  public CreateAccountGallery(Context context) {
    super(context);
  }

  public CreateAccountGallery(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return false;
  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    boolean handled = false;

    if (getFocusedChild() != null) {
      handled = getFocusedChild().dispatchKeyEvent(event);
    }

    if (!handled) {
      handled = event.dispatch(this, null, null);
    }

    return handled;
  }
}
