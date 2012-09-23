/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack007.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class Rectangle extends View {
  public static final int MAX_SIZE = 40;
  private static final int ALPHA = 255;
  private int mCoordX = 0;
  private int mCoordY = 0;
  private int mRealSize = 40;
  private int mSpeedX = 3;
  private int mSpeedY = 3;

  private boolean goRight = true;
  private boolean goDown = true;
  private DrawView mDrawView;

  private Paint mInnerPaint;
  private RectF mDrawRect;

  public Rectangle(Context context, DrawView drawView) {
    super(context);
    mDrawView = drawView;

    mInnerPaint = new Paint();

    mDrawRect = new RectF();

    /* Red is default */
    mInnerPaint.setARGB(ALPHA, 255, 0, 0);
    mInnerPaint.setAntiAlias(true);
  }

  public void setARGB(int a, int r, int g, int b) {
    mInnerPaint.setARGB(a, r, g, b);
  }

  public void setX(int newValue) {
    mCoordX = newValue;
  }

  public int getX() {
    return mCoordX;
  }

  public void setY(int newValue) {
    mCoordY = newValue;
  }

  public int getY() {
    return mCoordY;
  }

  public void move() {
    moveTo(mSpeedX, mSpeedY);
  }

  private void moveTo(int goX, int goY) {

    // check the borders, and set the direction if a border has reached
    if (mCoordX > (mDrawView.width - MAX_SIZE)) {
      goRight = false;
    }

    if (mCoordX < 0) {
      goRight = true;
    }

    if (mCoordY > (mDrawView.height - MAX_SIZE)) {
      goDown = false;
    }
    if (mCoordY < 0) {
      goDown = true;
    }

    // move the x and y
    if (goRight) {
      mCoordX += goX;
    } else {
      mCoordX -= goX;
    }
    if (goDown) {
      mCoordY += goY;
    } else {
      mCoordY -= goY;
    }

  }

  public int getSpeedX() {
    return mSpeedX;
  }

  public void setSpeedX(int speedX) {
    mSpeedX = speedX;
  }

  public int getmSpeedY() {
    return mSpeedY;
  }

  public void setSpeedY(int speedY) {
    mSpeedY = speedY;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    mDrawRect.set(mCoordX, mCoordY, mCoordX + mRealSize, mCoordY
        + mRealSize);
    canvas.drawRoundRect(mDrawRect, 0, 0, mInnerPaint);

  }

  public void setSize(int newSize) {
    mRealSize = newSize;
  }

  public int getSize() {
    return mRealSize;
  }
}
