/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack014;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class MainActivity extends Activity {

  private VideoView mVideoView;
  private View mPortraitPosition;
  private View mPortraitContent;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mPortraitPosition = findViewById(R.id.main_portrait_position);
    mPortraitContent = findViewById(R.id.main_portrait_content);
    mVideoView = (VideoView) findViewById(R.id.main_videoview);

    // We use a post to call initVideoView because
    // we need the width and height of mPortraitPosition
    mVideoView.post(new Runnable() {
      @Override
      public void run() {
        initVideoView();
      }
    });
  }

  private void initVideoView() {
    mVideoView.setMediaController(new MediaController(this));
    Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
        + R.raw.bigbuck);
    mVideoView.setVideoURI(uri);

    setVideoViewPosition();
    mVideoView.start();
  }

  private void setVideoViewPosition() {
    switch (getResources().getConfiguration().orientation) {
    case Configuration.ORIENTATION_LANDSCAPE: {
      mPortraitContent.setVisibility(View.GONE);

      RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
          LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

      params.addRule(RelativeLayout.CENTER_IN_PARENT);
      mVideoView.setLayoutParams(params);
      break;
    }

    case Configuration.ORIENTATION_SQUARE:
    case Configuration.ORIENTATION_UNDEFINED:
    case Configuration.ORIENTATION_PORTRAIT:
    default: {

      mPortraitContent.setVisibility(View.VISIBLE);

      int[] locationArray = new int[2];
      mPortraitPosition.getLocationOnScreen(locationArray);

      RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
          mPortraitPosition.getWidth(), mPortraitPosition.getHeight());

      params.leftMargin = locationArray[0];
      params.topMargin = locationArray[1];

      mVideoView.setLayoutParams(params);

      break;
    }
    }
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    setVideoViewPosition();
    super.onConfigurationChanged(newConfig);
  }
}
