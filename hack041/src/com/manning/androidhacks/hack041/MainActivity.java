/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack041;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.manning.androidhacks.hack041.data.Article;
import com.manning.androidhacks.hack041.data.ArticleAuthor;
import com.manning.androidhacks.hack041.data.ArticleCategory;
import com.manning.androidhacks.hack041.data.Author;
import com.manning.androidhacks.hack041.data.Category;
import com.manning.androidhacks.hack041.data.DatabaseHelper;

/**
 * This activity will be a ListView of Categories, and any Articles that belong
 * to the Category. The purpose of this activity is to demo inserting data, as
 * well as performing queries using OrmLite.
 */
public class MainActivity extends Activity {
  private LoadArticleListTask task;
  private ProgressDialog dialog;
  private ListView listView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    listView = (ListView) findViewById(R.id.listView);

    dialog = ProgressDialog.show(this, null, "Loading Articles", true,
        false);
    /*
     * Kick off a task (if needed) to load the data for this activity. Note: We
     * must be mindful of screen orientation changes here.
     */
    task = (LoadArticleListTask) getLastNonConfigurationInstance();
    if (task == null) {
      task = new LoadArticleListTask(this);
      task.execute();
    } else {
      task.attach(this);
    }

  }

  @Override
  public Object onRetainNonConfigurationInstance() {
    task.detach();
    return task;
  }

  public void handleDataLoaded(ArticleListModel model) {
    listView.setAdapter(new MainAdapter(model));
    dialog.dismiss();
  }

  private class MainAdapter extends BaseAdapter {
    private final static int TYPE_CATEGORY = 0, TYPE_ARTICLE = 1;

    private ArticleListModel model;
    private LayoutInflater inflater;
    SimpleDateFormat formatter = new SimpleDateFormat(
        "'Created on' M/d/yyyy h:mm a");

    View.OnClickListener listener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Article article = (Article) view.getTag();
        MainActivity.this.startActivity(new Intent(MainActivity.this,
            ArticleActivity.class).putExtra(ArticleActivity.ARTICLE_ID,
            article.getId()));
      }
    };

    private MainAdapter(ArticleListModel model) {
      this.model = model;
      inflater = LayoutInflater.from(MainActivity.this);
    }

    @Override
    public int getCount() {
      return model.flattenedItems.size();
    }

    @Override
    public Object getItem(int i) {
      return model.flattenedItems.get(i);
    }

    @Override
    public long getItemId(int i) {
      return i;
    }

    @Override
    public int getViewTypeCount() {
      return 2; // We want to show some category separators in the
                // ListView;
    }

    @Override
    public int getItemViewType(int position) {
      Object item = getItem(position);
      return item instanceof CategoryModel ? TYPE_CATEGORY
          : TYPE_ARTICLE;
    }

    @Override
    public boolean isEnabled(int position) {
      return getItemViewType(position) == TYPE_ARTICLE;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
      Object item = getItem(position);
      int itemViewType = getItemViewType(position);

      // Here we inflate/fill out the correct view based off the type of
      // the item we're processing.
      if (itemViewType == TYPE_CATEGORY) {
        CategoryModel model = (CategoryModel) item;
        if (view == null)
          view = inflater.inflate(R.layout.list_category, null);
        // Just fill out the display text for a CategoryModel
        ((TextView) view).setText(model.getText());
      } else {
        Article article = (Article) item;
        if (view == null)
          view = inflater.inflate(R.layout.list_item, null);
        // This demo will just show a title and created on date for
        // articles.
        ((TextView) view.findViewById(R.id.list_complex_title))
            .setText(article.getTitle());
        ((TextView) view.findViewById(R.id.list_complex_caption))
            .setText(formatter.format(article.getPublishedDate()));
        // Hook up a click listener
        view.setTag(article);
        view.setOnClickListener(listener);
      }

      return view;
    }
  }

  private static class ArticleListModel {
    List<Object> flattenedItems = new ArrayList<Object>();
  }

  /**
   * A helper class for formatting our output in this Activity's ListView.
   */
  private static class CategoryModel implements
      Comparable<CategoryModel> {
    Category category;
    List<Article> articleList = new ArrayList<Article>();

    private CategoryModel(Category category) {
      if (category == null)
        throw new IllegalArgumentException(
            "Passed in category must be non-null!");
      this.category = category;
    }

    public Integer getId() {
      return category.getId();
    }

    public String getText() {
      return category.formatFullName();
    }

    public void addArticle(Article a) {
      articleList.add(a);
    }

    @Override
    public int compareTo(CategoryModel that) {
      return this.category.formatFullName().compareToIgnoreCase(
          that.category.formatFullName());
    }
  }

  private static class LoadArticleListTask extends
      AsyncTask<Void, Void, ArticleListModel> {
    private MainActivity host;
    private ArticleListModel payload;
    private boolean done;

    private LoadArticleListTask(MainActivity host) {
      this.host = host;
    }

    @Override
    protected ArticleListModel doInBackground(Void... voids) {
      final DatabaseHelper helper = DatabaseHelper.getInstance(host);
      return helper.callInTransaction(new Callable<ArticleListModel>() {
        @Override
        public ArticleListModel call() throws SQLException {
          Dao<Article, Integer> articleDao = helper.getArticleDao();
          // Check if we need to insert our sample data.
          if (articleDao.countOf() == 0)
            insertSampleData(helper);

          /*
           * This activity will display all categories and articles. So, we will
           * query the cross reference table, and use the ORM to fill in our
           * data objects.
           */
          Dao<ArticleCategory, Void> articleCategoryDao = helper
              .getArticleCategoryDao();
          Map<Integer, CategoryModel> allCategories = new HashMap<Integer, CategoryModel>();
          for (ArticleCategory mapping : articleCategoryDao
              .queryForAll()) {
            // Build a unique set of Categories from the
            // data that was returned.
            CategoryModel model = new CategoryModel(mapping
                .getCategory());
            Integer key = model.getId();

            if (allCategories.containsKey(key))
              allCategories.get(key).addArticle(mapping.getArticle());
            else {
              model.addArticle(mapping.getArticle());
              allCategories.put(key, model);
            }
          }

          // Sort the categories, and return the data.
          List<CategoryModel> sorted = new ArrayList<CategoryModel>(
              allCategories.values());
          Collections.sort(sorted);

          ArticleListModel returnValue = new ArticleListModel();
          for (CategoryModel categoryModel : sorted) {
            returnValue.flattenedItems.add(categoryModel);
            returnValue.flattenedItems
                .addAll(categoryModel.articleList);
          }

          return returnValue;
        }
      });
    }

    /**
     * Called for the first run of the application (or when all Articles have
     * been deleted).
     * 
     * @param helper
     *          An instance of our DatabaseHelper.
     * @throws SQLException
     */
    private void insertSampleData(DatabaseHelper helper)
        throws SQLException {
      final int SAMPLE_COUNT = 50;
      final String SAMPLE_ARTICLE_TEXT = "Nulla nec neque sit amet libero molestie commodo sit amet quis diam. In non sapien enim. Mauris quis ipsum nec ipsum faucibus pretium sit amet blandit sapien. Nam ut magna et nisi molestie adipiscing sed eget justo. In fringilla risus in felis posuere placerat. Aenean quis orci mollis libero pellentesque euismod a nec mauris. Fusce tempus velit ut nisi lobortis congue! Pellentesque adipiscing metus luctus felis venenatis scelerisque? Praesent rhoncus iaculis nulla et sollicitudin. Proin placerat, ante vel varius vehicula, augue est cursus est, sed tristique orci quam ac massa? Mauris sodales tincidunt nibh, a sollicitudin magna volutpat vitae. Sed vitae ligula vel nibh imperdiet accumsan eu nec ipsum. Quisque laoreet, elit id dictum mollis, elit justo facilisis risus, ac interdum leo mi nec tortor.\n"
          + "\n"
          + "Aliquam tincidunt dapibus ipsum, sit amet varius sapien imperdiet pellentesque. Nulla vel eros est. Cras eu tellus et ipsum malesuada fermentum! Curabitur ac ipsum nec est fermentum ultrices at in felis. Pellentesque pellentesque nisi quis eros viverra porta! Integer in ipsum nulla, eu feugiat turpis. Donec pretium, urna dignissim aliquam convallis; ante nisl porttitor orci, eget eleifend tellus nisi a libero!";
      Author[] authors = new Author[] {
          new Author("First Author", "first@example.com"),
          new Author("Second Author", "second@example.com"),
          new Author("Third Author", "third@example.com") };
      Category[] topCategories = new Category[] {
          new Category("Android Layouts", null),
          new Category("Databases", null),
          new Category("Network Communication", null),
          new Category("Screen Rotation", null),
          new Category("Threading", null) };
      Category[] subCategories = new Category[] { new Category("SQL",
          topCategories[1]) };

      // We will need a few DAO objects from our OrmLiteSqliteOpenHelper
      // instance
      Dao<Author, Integer> authorDao = helper.getAuthorDao();
      Dao<Category, Integer> categoryDao = helper.getCategoryDao();
      Dao<Article, Integer> articleDao = helper.getArticleDao();
      Dao<ArticleAuthor, Void> articleAuthorDao = helper
          .getArticleAuthorDao();
      Dao<ArticleCategory, Void> articleCategoryDao = helper
          .getArticleCategoryDao();

      // Insert all of our sample authors. The DAO will set the database
      // generated ID, which we will need later.
      for (Author author : authors)
        authorDao.create(author);

      // Insert all sample categories
      for (Category category : topCategories)
        categoryDao.create(category);
      for (Category category : subCategories)
        categoryDao.create(category);

      for (int i = 0; i < SAMPLE_COUNT; i++) {
        // Make a new Article instance, and call create() on the DAO.
        // That call will set the ID of the object.
        Article article = new Article(new Date(), SAMPLE_ARTICLE_TEXT,
            "Article " + i);
        articleDao.create(article);

        // Insert cross reference(s) to set the author(s) of this
        // article
        if (i == 0) {
          // Let's make the first article have many authors.
          for (Author author : authors)
            articleAuthorDao.create(new ArticleAuthor(author, article));
        } else {
          // Otherwise, we'll assign a single author to the article.
          Author author = authors[i % authors.length];
          articleAuthorDao.create(new ArticleAuthor(author, article));
        }

        // Insert another cross reference to set categories for this
        // article.
        if (i == 15) {
          // For some diversity, we will put this article in a sub
          // category
          articleCategoryDao.create(new ArticleCategory(article,
              subCategories[0]));
        } else {
          int catIndex = i / (SAMPLE_COUNT / topCategories.length);
          Category category = topCategories[catIndex];
          articleCategoryDao.create(new ArticleCategory(article,
              category));
        }
      }
    }

    @Override
    protected void onPostExecute(ArticleListModel articleListModel) {
      done = true;
      payload = articleListModel;
      host.handleDataLoaded(articleListModel);
    }

    public void detach() {
      host = null;
    }

    public void attach(MainActivity newActivity) {
      host = newActivity;
      if (done)
        host.handleDataLoaded(payload);
    }
  }
}
