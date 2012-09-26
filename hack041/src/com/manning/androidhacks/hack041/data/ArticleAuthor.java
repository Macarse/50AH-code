/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack041.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * A cross reference object to persist the many-to-many relation between authors
 * and articles.
 */
@DatabaseTable(tableName = ArticleAuthor.TABLE_NAME)
public class ArticleAuthor {
  public static final String TABLE_NAME = "articleauthors",
      ARTICLE_ID_COLUMN = "article_id", AUTHOR_ID_COLUMN = "author_id";

  @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true, uniqueCombo = true, columnName = AUTHOR_ID_COLUMN, columnDefinition = "integer references "
      + Author.TABLE_NAME
      + "("
      + Author.ID_COLUMN
      + ") on delete cascade")
  private Author author;

  @DatabaseField(foreign = true, canBeNull = false, uniqueCombo = true, columnName = ARTICLE_ID_COLUMN, columnDefinition = "integer references "
      + Article.TABLE_NAME
      + "("
      + Article.ID_COLUMN
      + ") on delete cascade")
  private Article article;

  public ArticleAuthor() {

  }

  public ArticleAuthor(Author author, Article article) {
    this.author = author;
    this.article = article;
  }

  public Article getArticle() {
    return article;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }
}
