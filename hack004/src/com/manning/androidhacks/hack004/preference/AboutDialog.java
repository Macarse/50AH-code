/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack004.preference;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.DialogPreference;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutDialog extends DialogPreference {

  private Context mContext;
  private String mVersionNumber;

  public AboutDialog(Context context) {
    this(context, null);
  }

  public AboutDialog(Context context, AttributeSet attrs) {
    this(context, attrs, 0);

  }

  public AboutDialog(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    mContext = context;
  }

  @Override
  protected View onCreateDialogView() {
    LinearLayout layout = new LinearLayout(mContext);
    layout.setOrientation(LinearLayout.VERTICAL);
    TextView splashText = new TextView(mContext);
    String fmt = "Version %s<br />"
        + "<a href=\"http://manning.com/sessa\">MoreInfo</a>";
    try {
      String pkg = mContext.getPackageName();
      mVersionNumber = mContext.getPackageManager().getPackageInfo(pkg,
          0).versionName;
    } catch (NameNotFoundException e) {
      e.printStackTrace();
    }

    if (mVersionNumber != null) {
      String aboutMsg = String.format(fmt, mVersionNumber);
      splashText.setText(Html.fromHtml(aboutMsg));
      splashText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    layout.addView(splashText);

    return layout;
  }
}
