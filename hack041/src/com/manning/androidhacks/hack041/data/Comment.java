/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack041.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * A simple Author object annotated for use with OrmLite.
 */
@DatabaseTable(tableName = Comment.TABLE_NAME)
public class Comment {
  public static final String TABLE_NAME = "comments",
      ID_COLUMN = "_id", USER_COLUMN = "user", TEXT_COLUMN = "text",
      ADDED_DATE_COLUMN = "dateAdded", ARTICLE_COLUMN = "article";

  @DatabaseField(generatedId = true, columnName = ID_COLUMN)
  private int id;

  @DatabaseField(columnName = USER_COLUMN)
  private String user;

  @DatabaseField(canBeNull = false, columnName = TEXT_COLUMN)
  private String text;

  @DatabaseField(canBeNull = false, columnName = ADDED_DATE_COLUMN)
  private Date dateAdded;

  @DatabaseField(foreign = true, canBeNull = false, columnName = ARTICLE_COLUMN, columnDefinition = "integer references "
      + Article.TABLE_NAME
      + "("
      + Article.ID_COLUMN
      + ") on delete cascade")
  private Article article;

  public Comment() {

  }

  public Comment(Article article, Date dateAdded, String text,
      String user) {
    this.article = article;
    this.dateAdded = dateAdded;
    this.text = text;
    this.user = user;
  }

  public Comment(Article article, Date dateAdded, int id, String text,
      String user) {
    this.article = article;
    this.dateAdded = dateAdded;
    this.id = id;
    this.text = text;
    this.user = user;
  }

  public Article getArticle() {
    return article;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

  public Date getDateAdded() {
    return dateAdded;
  }

  public void setDateAdded(Date dateAdded) {
    this.dateAdded = dateAdded;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }
}
