/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack006;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getListView().setLayoutAnimation(
        new LayoutAnimationController(AnimationUtils.loadAnimation(
            this, R.anim.list_animation), 0.5f));

    setListAdapter(new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, Countries.COUNTRIES));
  }
}
