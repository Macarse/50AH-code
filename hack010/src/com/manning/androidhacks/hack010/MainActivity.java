/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack010;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class MainActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    final TextView textView1 = (TextView) findViewById(R.id.my_text_view_html);
    textView1.setText(Html.fromHtml(getString(R.string.text1)));
    textView1.setMovementMethod(LinkMovementMethod.getInstance());

    final Spannable text2 = new SpannableString(
        getString(R.string.text2));
    text2.setSpan(new BackgroundColorSpan(Color.RED), 1, 4, 0);
    text2.setSpan(new ForegroundColorSpan(Color.BLUE), 5, 9, 0);

    ((TextView) findViewById(R.id.my_text_view_spannable))
        .setText(text2);
  }
}
