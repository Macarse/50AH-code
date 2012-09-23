/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack008;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;

public class MainActivity extends Activity implements AnimatorListener {

  private static final int ANIM_COUNT = 4;
  private static final int[] PHOTOS = new int[] { R.drawable.photo1,
    R.drawable.photo2, R.drawable.photo3, R.drawable.photo4,
    R.drawable.photo5, R.drawable.photo6 };

  private FrameLayout mContainer;
  private ImageView mView;
  private Random mRandom = new Random();
  private int mIndex = 0;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mContainer = new FrameLayout(this);
    mContainer.setLayoutParams(new LayoutParams(
        LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

    mView = createNewView();
    mContainer.addView(mView);

    setContentView(mContainer);
  }

  private ImageView createNewView() {
    ImageView ret = new ImageView(this);
    ret.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
        LayoutParams.FILL_PARENT));
    ret.setScaleType(ScaleType.FIT_XY);
    ret.setImageResource(PHOTOS[mIndex]);
    mIndex = (mIndex + 1 < PHOTOS.length) ? mIndex + 1 : 0;

    return ret;
  }

  @Override
  protected void onResume() {
    super.onResume();
    nextAnimation();
  }

  private void nextAnimation() {
    AnimatorSet anim = new AnimatorSet();
    final int index = mRandom.nextInt(ANIM_COUNT);

    switch (index) {
    case 0:
      anim.playTogether(
          ObjectAnimator.ofFloat(mView, "scaleX", 1.5f, 1f),
          ObjectAnimator.ofFloat(mView, "scaleY", 1.5f, 1f));
      break;

    case 1:
      anim.playTogether(
          ObjectAnimator.ofFloat(mView, "scaleX", 1, 1.5f),
          ObjectAnimator.ofFloat(mView, "scaleY", 1, 1.5f));
      break;

    case 2:
      AnimatorProxy.wrap(mView).setScaleX(1.5f);
      AnimatorProxy.wrap(mView).setScaleY(1.5f);
      anim.playTogether(ObjectAnimator.ofFloat(mView,
          "translationY", 80f, 0f));
      break;

    case 3:
    default:
      AnimatorProxy.wrap(mView).setScaleX(1.5f);
      AnimatorProxy.wrap(mView).setScaleY(1.5f);
      anim.playTogether(ObjectAnimator.ofFloat(mView,
          "translationX", 0f, 40f));
      break;
    }

    anim.setDuration(3000);
    anim.addListener(this);
    anim.start();
  }

  @Override
  public void onAnimationCancel(Animator arg0) {
  }

  @Override
  public void onAnimationEnd(Animator animator) {
    mContainer.removeView(mView);
    mView = createNewView();
    mContainer.addView(mView);
    nextAnimation();
  }

  @Override
  public void onAnimationRepeat(Animator arg0) {
  }

  @Override
  public void onAnimationStart(Animator arg0) {
  }
}
