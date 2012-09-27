package com.nasatrainedmonkeys.roboinstaller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements Runnable {
  ProgressDialog progress;
  TextView text;
  Button button;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.main);

    text = (TextView) findViewById(R.id.text);

    button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        progress = ProgressDialog.show(MainActivity.this,
            "Installing...", "");

        Thread thread = new Thread(MainActivity.this);
        thread.start();
      }
    });
  }

  @Override
  public void run() {
    RoboInstaller installer = new RoboInstaller(getApplicationContext());
    installer.installScalaLibs();

    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        button.setEnabled(false);

        text.setTextSize(20);
        text.setText("Finished! Please restart your phone.");

        progress.dismiss();
      }
    });
  }
}
