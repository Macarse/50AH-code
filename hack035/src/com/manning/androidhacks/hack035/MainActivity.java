/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack035;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

  private static final int PICK_PICTURE = 10;
  private static final int TAKE_PICTURE = 11;
  private static final int PICK_OR_TAKE_PICTURE = 12;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void onTakePicture(View v) {
    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    Intent chooserIntent = Intent.createChooser(takePhotoIntent,
        getString(R.string.activity_main_pick_picture));
    startActivityForResult(chooserIntent, TAKE_PICTURE);
  }

  public void onPickPicture(View v) {
    Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
    pickIntent.setType("image/*");
    Intent chooserIntent = Intent.createChooser(pickIntent,
        getString(R.string.activity_main_take_picture));
    startActivityForResult(chooserIntent, PICK_PICTURE);
  }

  public void onPickBoth(View v) {
    Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
    pickIntent.setType("image/*");

    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    Intent chooserIntent = Intent.createChooser(pickIntent,
        getString(R.string.activity_main_pick_both));
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
        new Intent[] { takePhotoIntent });

    startActivityForResult(chooserIntent, PICK_OR_TAKE_PICTURE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode,
      Intent data) {
    if (resultCode != RESULT_OK) {
      super.onActivityResult(requestCode, resultCode, data);
      return;
    }

    Toast.makeText(this,
        "onActivityResult with req code: " + requestCode,
        Toast.LENGTH_SHORT).show();
  }
}
