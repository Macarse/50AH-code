/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack041.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * A cross reference object to persist the many-to-many relation between
 * articles and categories.
 */
@DatabaseTable(tableName = ArticleCategory.TABLE_NAME)
public class ArticleCategory {
  public static final String TABLE_NAME = "articlecategories",
      ARTICLE_ID_COLUMN = "article_id",
      CATEGORY_ID_COLUMN = "category_id";

  @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, uniqueCombo = true, columnName = ARTICLE_ID_COLUMN, columnDefinition = "integer references "
      + Article.TABLE_NAME
      + "("
      + Article.ID_COLUMN
      + ") on delete cascade")
  private Article article;

  @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, uniqueCombo = true, columnName = CATEGORY_ID_COLUMN, columnDefinition = "integer references "
      + Category.TABLE_NAME
      + "("
      + Category.ID_COLUMN
      + ") on delete cascade")
  private Category category;

  public ArticleCategory() {
  }

  public ArticleCategory(Article article, Category category) {
    this.article = article;
    this.category = category;
  }

  public Article getArticle() {
    return article;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
}
