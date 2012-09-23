/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack017;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Gallery;
import android.widget.Toast;

import com.manning.androidhacks.hack017.CreateAccountAdapter.CreateAccountDelegate;
import com.manning.androidhacks.hack017.model.Account;

public class CreateAccountActivity extends Activity implements
    CreateAccountDelegate {

  private Gallery mGallery;
  private CreateAccountAdapter mAdapter;
  private int mGalleryPosition;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.create_account);
    mGallery = (Gallery) findViewById(R.id.create_account_gallery);

    mAdapter = new CreateAccountAdapter(this);
    mGallery.setAdapter(mAdapter);
    mGalleryPosition = 0;
  }

  @Override
  protected void onResume() {
    super.onResume();
    mAdapter.setDelegate(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mAdapter.setDelegate(null);
  }

  @Override
  public void onBackPressed() {

    if (mGalleryPosition > 0) {
      scroll(BACKWARD);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public void scroll(int type) {

    switch (type) {
    case FORWARD:
    default:

      if (mGalleryPosition < mGallery.getCount() - 1) {
        mGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, new KeyEvent(0,
            0));
        mGalleryPosition++;
      }
      break;

    case BACKWARD:
      if (mGalleryPosition > 0) {
        mGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, new KeyEvent(0,
            0));
        mGalleryPosition--;
      }
    }
  }

  @Override
  public void processForm(Account account) {

    HashMap<String, String> errors = account.getErrors();
    String email = account.getEmail();

    if (null == email) {
      errors.put(CreateAccountAdapter.EMAIL_KEY, "E-mail is empty");
    } else if (email.toLowerCase().equals("me@my.com")) {
      errors.put(CreateAccountAdapter.EMAIL_KEY,
          "E-mail is already taken");
    }

    if (errors.isEmpty()) {
      Toast.makeText(this, "Form ok!", Toast.LENGTH_SHORT).show();
      finish();
    } else {
      mAdapter.showErrors(account);
      mGallery.setSelection(0);
      mGalleryPosition = 0;
    }
  }

}
