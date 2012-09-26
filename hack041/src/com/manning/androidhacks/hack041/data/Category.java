/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack041.data;

import java.util.ArrayList;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * A simple Category object annotated for use with OrmLite.
 */
@DatabaseTable(tableName = Category.TABLE_NAME)
public class Category {
  /*
   * Final Strings for building queries by hand. Define these in one place to
   * support refactoring, if needed.
   */
  public static final String TABLE_NAME = "categories",
      ID_COLUMN = "_id", NAME_COLUMN = "name",
      PARENT_COLUMN = "parent";

  /* Member variables that will map to columns in the SQL database. */

  @DatabaseField(generatedId = true, columnName = ID_COLUMN)
  private int id;

  @DatabaseField(canBeNull = false, columnName = NAME_COLUMN)
  private String name;

  @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = true, columnName = PARENT_COLUMN, columnDefinition = "integer references "
      + TABLE_NAME + "(" + ID_COLUMN + ") on delete cascade")
  private Category parent;

  public Category() {
    // OrmLite requires a parameterless constructor with at least default
    // access.
  }

  public Category(String name, Category parent) {
    this.name = name;
    this.parent = parent;
  }

  public Category(int id, String name, Category parent) {
    this.id = id;
    this.name = name;
    this.parent = parent;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Category getParent() {
    return parent;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setParent(Category parent) {
    this.parent = parent;
  }

  private String lazyFormattedName;

  /**
   * Formats this category's name, with the names of any non-null parents.
   * 
   * @return The formatted full name of this category.
   */
  public String formatFullName() {
    if (lazyFormattedName != null)
      return lazyFormattedName;

    final String DELIMITER = " > ";
    ArrayList<String> parts = new ArrayList<String>();
    Category current = this;
    while (current != null) {
      parts.add(current.getName());
      current = current.getParent();
    }

    StringBuilder sb = new StringBuilder();
    for (int i = parts.size() - 1; i >= 0; i--) {
      sb.append(parts.get(i));
      if (i != 0)
        sb.append(DELIMITER);
    }
    lazyFormattedName = sb.toString();
    return lazyFormattedName;
  }

}
