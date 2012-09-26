/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack041.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * A simple Author object annotated for use with OrmLite.
 */
@DatabaseTable(tableName = Author.TABLE_NAME)
public class Author implements Comparable<Author> {
  public static final String TABLE_NAME = "authors", ID_COLUMN = "_id",
      NAME_COLUMN = "name", EMAIL_COLUMN = "email";

  @DatabaseField(generatedId = true, columnName = ID_COLUMN)
  private int id;

  @DatabaseField(canBeNull = false, columnName = NAME_COLUMN)
  private String name;

  @DatabaseField(columnName = EMAIL_COLUMN)
  private String email;

  public Author() {
    // OrmLite requires a parameterless constructor with at least default
    // access.
  }

  public Author(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public Author(int id, String name, String email) {
    this.email = email;
    this.id = id;
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int compareTo(Author other) {
    return name.compareTo(other.getName());
  }
}
