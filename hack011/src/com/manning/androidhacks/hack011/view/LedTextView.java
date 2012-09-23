/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack011.view;

import java.io.File;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class LedTextView extends TextView {

  private static final String FONTS_FOLDER = "fonts";
  private static final String FONT_DIGITAL_7 = FONTS_FOLDER
      + File.separator + "digital-7.ttf";

  public LedTextView(Context context) {
    super(context);
    init(context);
  }

  public LedTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public LedTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context);
  }

  private void init(Context context) {
    AssetManager assets = context.getAssets();
    final Typeface font = Typeface.createFromAsset(assets,
        FONT_DIGITAL_7);
    setTypeface(font);
  }

}
