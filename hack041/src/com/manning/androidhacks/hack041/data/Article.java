/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack041.data;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * A simple Article object annotated for use with OrmLite.
 */
@DatabaseTable(tableName = Article.TABLE_NAME)
public class Article {
  public static final String TABLE_NAME = "articles",
      ID_COLUMN = "_id", TITLE_COLUMN = "title", TEXT_COLUMN = "text",
      PUBLISHED_DATE_COLUMN = "publishedDate";

  @DatabaseField(generatedId = true, columnName = ID_COLUMN)
  private int id;

  @DatabaseField(canBeNull = false, columnName = TITLE_COLUMN)
  private String title;

  @DatabaseField(canBeNull = false, columnName = TEXT_COLUMN)
  private String text;

  @DatabaseField(canBeNull = false, columnName = PUBLISHED_DATE_COLUMN)
  private Date publishedDate;

  /*
   * No column will be created on the articles table, but the ORM will help us
   * populate this collection from a different table.
   */
  @ForeignCollectionField(eager = true)
  ForeignCollection<Comment> comments;

  public Article() {
    // OrmLite requires a parameterless constructor with at least default
    // access.
  }

  public Article(Date publishedDate, String text, String title) {
    this.publishedDate = publishedDate;
    this.text = text;
    this.title = title;
  }

  public Article(Date publishedDate, int id, String text, String title) {
    this.publishedDate = publishedDate;
    this.id = id;
    this.text = text;
    this.title = title;
  }

  public int getId() {
    return id;
  }

  public Date getPublishedDate() {
    return publishedDate;
  }

  public String getText() {
    return text;
  }

  public String getTitle() {
    return title;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setPublishedDate(Date publishedDate) {
    this.publishedDate = publishedDate;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ForeignCollection<Comment> getComments() {
    return comments;
  }
}
