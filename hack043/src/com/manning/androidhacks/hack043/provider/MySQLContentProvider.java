package com.manning.androidhacks.hack043.provider;

import java.util.HashMap;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class MySQLContentProvider extends SQLiteContentProvider {
  public static final String TABLE_NAME = "sql_content_provider_numbers";
  public static final String AUTHORITY = MySQLContentProvider.class
      .getCanonicalName();

  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_TEXT = "text";

  private static final int ITEM = 1;
  private static final int ITEM_ID = 2;

  public static final String DEFAULT_SORT_ORDER = "_id ASC";

  private static HashMap<String, String> projectionMap;
  private static final UriMatcher sUriMatcher;

  public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.androidhacks.sqlcpnumber";
  public static final String CONTENT_TYPE_ID = "vnd.android.cursor.item/vnd.androidhacks.sqlcpnumber";

  public static final Uri CONTENT_URI = Uri.parse("content://"
      + AUTHORITY + "/" + TABLE_NAME);

  static {
    sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    sUriMatcher.addURI(AUTHORITY, TABLE_NAME, ITEM);
    sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", ITEM_ID);

    projectionMap = new HashMap<String, String>();
    projectionMap.put(COLUMN_ID, COLUMN_ID);
    projectionMap.put(COLUMN_TEXT, COLUMN_TEXT);
  }

  @Override
  protected SQLiteOpenHelper getDatabaseHelper(Context context) {
    return new DatabaseHelper(context);
  }

  @Override
  protected Uri insertInTransaction(Uri uri, ContentValues values) {
    String table = null;

    switch (sUriMatcher.match(uri)) {
    case ITEM:
      table = TABLE_NAME;
      break;
    default:
      new RuntimeException("Invalid URI for inserting: " + uri);
    }

    long rowId = mDb.insert(table, null, values);

    if (rowId > 0) {
      Uri noteUri = ContentUris.withAppendedId(uri, rowId);
      return noteUri;
    }

    throw new SQLException("Failed to insert row into " + uri);
  }

  @Override
  protected int updateInTransaction(Uri uri, ContentValues values,
      String selection, String[] selectionArgs) {

    int count = 0;
    switch (sUriMatcher.match(uri)) {
    case ITEM:
      count = mDb.update(TABLE_NAME, values, selection, selectionArgs);
      break;
    case ITEM_ID:
      count = mDb.update(
          TABLE_NAME,
          values,
          COLUMN_ID
              + "="
              + uri.getPathSegments().get(1)
              + (!TextUtils.isEmpty(selection) ? " AND (" + selection
                  + ")" : ""), selectionArgs);
      break;
    default:
      throw new RuntimeException("Unknown URI " + uri);
    }

    return count;
  }

  @Override
  protected int deleteInTransaction(Uri uri, String selection,
      String[] selectionArgs) {

    int count;

    switch (sUriMatcher.match(uri)) {
    case ITEM:
      count = mDb.delete(TABLE_NAME, selection, selectionArgs);
      break;
    case ITEM_ID:
      String id = uri.getPathSegments().get(1);
      count = mDb.delete(TABLE_NAME,
          COLUMN_ID
              + "="
              + id
              + (!TextUtils.isEmpty(selection) ? " AND (" + selection
                  + ")" : ""), selectionArgs);
      break;
    default:
      throw new RuntimeException("Unkown URI: " + uri);
    }

    return count;
  }

  @Override
  protected void notifyChange() {
    getContext().getContentResolver().notifyChange(CONTENT_URI, null);
  }

  @Override
  public String getType(Uri uri) {
    switch (sUriMatcher.match(uri)) {
    case ITEM:
      return CONTENT_TYPE;
    case ITEM_ID:
      return CONTENT_TYPE_ID;
    default:
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
      String[] selectionArgs, String sortOrder) {

    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    switch (sUriMatcher.match(uri)) {
    case ITEM:
      qb.setTables(TABLE_NAME);
      qb.setProjectionMap(projectionMap);
      break;
    case ITEM_ID:
      qb.setTables(TABLE_NAME);
      qb.setProjectionMap(projectionMap);
      qb.appendWhere(COLUMN_ID + "=" + uri.getPathSegments().get(1));
      break;
    default:
      throw new RuntimeException("Unknown URI");
    }

    SQLiteDatabase db = getDatabaseHelper().getReadableDatabase();
    Cursor c = qb.query(db, projection, selection, selectionArgs, null,
        null, sortOrder);

    c.setNotificationUri(getContext().getContentResolver(), uri);
    return c;
  }
}
