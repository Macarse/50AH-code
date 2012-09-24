/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023;

import com.manning.androidhacks.hack023.dao.TodoDAO;
import com.manning.androidhacks.hack023.model.Todo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNewActivity extends Activity {
  private EditText mTitle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_todo);
    mTitle = (EditText) findViewById(R.id.add_todo_edittext);
  }

  public void addNew(View v) {
    String title = mTitle.getText().toString().trim();
    if (title != null && title.length() != 0) {
      Todo todo = new Todo();
      todo.setTitle(title);

      TodoDAO.getInstance().addNewTodo(getContentResolver(), todo);

      finish();
    }
  }

}
