/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack007.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class DrawView extends View {
  private Rectangle mRectangle;
  public int width;
  public int height;

  public DrawView(Context context) {
    super(context);

    mRectangle = new Rectangle(context, this);
    mRectangle.setARGB(255, 255, 0, 0);
    mRectangle.setSpeedX(3);
    mRectangle.setSpeedY(3);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    invalidate();

    mRectangle.move();
    mRectangle.onDraw(canvas);
  }

}
