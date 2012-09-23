/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack009;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.Toast;

public class MainActivity extends Activity {

  private static final int DATE_DIALOG_ID = 0;

  private int mYear;
  private int mMonth;
  private int mDay;
  private OnDateSetListener mDateSetListener;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // get the current date
    final Calendar c = Calendar.getInstance();
    mYear = c.get(Calendar.YEAR);
    mMonth = c.get(Calendar.MONTH);
    mDay = c.get(Calendar.DAY_OF_MONTH);

    mDateSetListener = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear,
          int dayOfMonth) {
        Toast.makeText(
            MainActivity.this,
            getString(R.string.picked_date_format, monthOfYear,
                dayOfMonth, year), Toast.LENGTH_SHORT).show();
      }
    };

    findViewById(R.id.details_date).setOnClickListener(
        new OnClickListener() {
          @Override
          public void onClick(View v) {
            showDialog(DATE_DIALOG_ID);
          }
        });
  }

  @Override
  protected Dialog onCreateDialog(int id) {
    switch (id) {
    case DATE_DIALOG_ID:
      return new DatePickerDialog(this, mDateSetListener, mYear,
          mMonth, mDay);
    }
    return super.onCreateDialog(id);
  }

}
