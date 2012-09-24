/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack025;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;

public class MainActivity extends ListActivity {

  private static final int MODEL_COUNT = 30;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setListAdapter(new ModelAdapter(this, 0, buildModels()));
  }

  private List<Model> buildModels() {
    final ArrayList<Model> ret = new ArrayList<Model>(MODEL_COUNT);
    for (int i = 0; i < MODEL_COUNT; i++) {
      final Model model = new Model();
      model.setImage(R.drawable.icon);
      model.setText1("Name " + i);
      model.setText2("Description " + i);
      ret.add(model);
    }
    return ret;
  }
}
