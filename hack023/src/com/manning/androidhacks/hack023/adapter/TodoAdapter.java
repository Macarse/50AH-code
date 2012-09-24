/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack023.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.manning.androidhacks.hack023.R;
import com.manning.androidhacks.hack023.dao.TodoDAO;
import com.manning.androidhacks.hack023.provider.StatusFlag;
import com.manning.androidhacks.hack023.provider.TodoContentProvider;

public class TodoAdapter extends CursorAdapter {

  private LayoutInflater mInflater;
  private final int mTitleIndex;
  private final int mInternalIdIndex;
  private final int mInternalStatusIndex;
  private Activity mActivity;

  private static final String[] PROJECTION_IDS_TITLE_AND_STATUS = new String[] {
      TodoContentProvider.COLUMN_ID, TodoContentProvider.COLUMN_TITLE,
      TodoContentProvider.COLUMN_STATUS_FLAG };

  public TodoAdapter(Activity activity) {
    super(activity, getManagedCursor(activity), true);
    mActivity = activity;
    mInflater = LayoutInflater.from(activity);
    final Cursor c = getCursor();

    mInternalIdIndex = c
        .getColumnIndexOrThrow(TodoContentProvider.COLUMN_ID);
    mTitleIndex = c
        .getColumnIndexOrThrow(TodoContentProvider.COLUMN_TITLE);
    mInternalStatusIndex = c
        .getColumnIndexOrThrow(TodoContentProvider.COLUMN_STATUS_FLAG);
  }

  private static Cursor getManagedCursor(Activity activity) {
    return activity.managedQuery(TodoContentProvider.CONTENT_URI,
        PROJECTION_IDS_TITLE_AND_STATUS,
        TodoContentProvider.COLUMN_STATUS_FLAG + " != "
            + StatusFlag.DELETE, null,
        TodoContentProvider.DEFAULT_SORT_ORDER);
  }

  @Override
  public void bindView(View view, Context context, Cursor c) {
    final ViewHolder holder = (ViewHolder) view.getTag();
    holder.id.setText(c.getString(mInternalIdIndex));
    holder.title.setText(c.getString(mTitleIndex));

    final int status = c.getInt(mInternalStatusIndex);
    if (StatusFlag.CLEAN != status) {
      holder.title.setBackgroundColor(Color.RED);
    } else {
      holder.title.setBackgroundColor(Color.GREEN);
    }

    final Long id = Long.valueOf(holder.id.getText().toString());
    holder.deleteButton.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        TodoDAO.getInstance().deleteTodo(
            mActivity.getContentResolver(), id);
      }
    });

  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    final View view = mInflater.inflate(R.layout.todo_row, parent,
        false);

    ViewHolder holder = new ViewHolder();
    holder.id = (TextView) view.findViewById(R.id.todo_row_id);
    holder.title = (TextView) view.findViewById(R.id.todo_row_title);
    holder.deleteButton = (Button) view
        .findViewById(R.id.todo_row_delete);

    view.setTag(holder);

    return view;
  }

  private static class ViewHolder {
    TextView id;
    TextView title;
    Button deleteButton;
  }

}
