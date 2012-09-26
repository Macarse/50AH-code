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
import java.util.List;
import java.util.concurrent.Callable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.manning.androidhacks.hack041.data.Article;
import com.manning.androidhacks.hack041.data.ArticleAuthor;
import com.manning.androidhacks.hack041.data.Author;
import com.manning.androidhacks.hack041.data.Comment;
import com.manning.androidhacks.hack041.data.DatabaseHelper;

/**
 * An Activity for showing more types of queries using OrmLite.
 */
public class ArticleActivity extends Activity {
  public static String ARTICLE_ID = "ARTICLE_ID";

  private LoadArticleTask task;
  private AddCommentTask commentTask;
  private ProgressDialog dialog;
  private int articleId;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.article);

    articleId = getIntent().getIntExtra(ARTICLE_ID, -1);
    if (articleId == -1)
      throw new RuntimeException("Extra must be passed in!");

    dialog = ProgressDialog.show(this, null, "Loading Articles", true,
        false);
    /*
     * Kick off a task (if needed) to load the data for this activity. Note: We
     * must be mindful of screen orientation changes here.
     */
    task = (LoadArticleTask) getLastNonConfigurationInstance();
    if (task == null) {
      task = new LoadArticleTask(this, articleId);
      task.execute();
    } else {
      task.attach(this);
    }
  }

  @Override
  public Object onRetainNonConfigurationInstance() {
    if (task != null)
      task.detach();

    if (commentTask != null)
      commentTask.detach(); // No need to retain this AsyncTask. Upon
                            // orientation change, the new transaction to
                            // read data will be serialized and see any
                            // pending update.

    return task;
  }

  public void handleDataLoaded(ArticleModel model) {
    dialog.dismiss();
    task.detach();
    task = null;

    // Fill out the views for this Activity.
    TextView title = (TextView) findViewById(R.id.article_title);
    title.setText(model.article.getTitle());

    TextView subTitle = (TextView) findViewById(R.id.article_sub_title);
    subTitle.setText(model.getSubTitleText());

    TextView articleText = (TextView) findViewById(R.id.article_text);
    articleText.setText(model.article.getText());

    findViewById(R.id.divider).setVisibility(View.VISIBLE);

    updateCommentCount(model.commentCount);

    // Setup click events.
    findViewById(R.id.addComment).setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            addCommentPrompt();
          }
        });
  }

  private void updateCommentCount(long count) {
    TextView commentCount = (TextView) findViewById(R.id.comment_count);
    commentCount.setText(String.format("Comments: %d", count));
  }

  private void addCommentPrompt() {
    View body = LayoutInflater.from(this).inflate(
        R.layout.comment_dialog, null);
    final EditText nameEditText = (EditText) body
        .findViewById(R.id.name);
    final EditText commentEditText = (EditText) body
        .findViewById(R.id.comment);

    new AlertDialog.Builder(this)
        .setTitle("Add Comment")
        .setView(body)
        .setPositiveButton(android.R.string.ok,
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                String name = nameEditText.getText().toString().trim();
                String comments = commentEditText.getText().toString()
                    .trim();
                if (!name.equals("") && !comments.equals("")) {
                  insertComment(name, comments);
                }
              }
            })
        .setNegativeButton(android.R.string.cancel,
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
              }
            }).show();
  }

  private void insertComment(final String name, final String comments) {
    commentTask = new AddCommentTask(this, articleId, name, comments);
    commentTask.execute();
  }

  private static class ArticleModel {
    private Article article;
    private List<Author> authorList = new ArrayList<Author>();
    private long commentCount;
    SimpleDateFormat formatter = new SimpleDateFormat(
        " 'on' M/d/yyyy h:mm a");

    private ArticleModel(Article article) {
      if (article == null)
        throw new IllegalArgumentException(
            "Passed in article must be non-null!");

      this.article = article;
    }

    public String getSubTitleText() {
      Collections.sort(authorList);
      StringBuilder sb = new StringBuilder("Created by");
      if (authorList.size() == 0)
        sb.append(" unknown");
      else {
        sb.append(" ").append(authorList.get(0).getName());
        if (authorList.size() > 1) {
          for (int i = 1; i < authorList.size(); i++)
            sb.append(", ").append(authorList.get(i).getName());
        }
      }
      sb.append(formatter.format(article.getPublishedDate()));

      return sb.toString();
    }
  }

  /**
   * Task to load the needed items out of the database. This will demo some more
   * ways to query a database with OrmLite.
   */
  private static class LoadArticleTask extends
      AsyncTask<Void, Void, ArticleModel> {
    private boolean done;
    private ArticleModel payload;
    private ArticleActivity host;
    private int articleId;

    private LoadArticleTask(ArticleActivity host, int id) {
      this.host = host;
      this.articleId = id;
    }

    @Override
    protected ArticleModel doInBackground(Void... voids) {
      final DatabaseHelper helper = DatabaseHelper.getInstance(host);
      // We will perform multiple queries on the database, so we will use
      // a transaction, which will help performance.
      return helper.callInTransaction(new Callable<ArticleModel>() {
        @Override
        public ArticleModel call() throws SQLException {
          // First, we use queryForId() to get the Article
          // itself.
          Dao<Article, Integer> articleDao = helper.getArticleDao();
          Article article = articleDao.queryForId(articleId);

          // Start to construct our return object, which will
          // hold all data needed to populate the Activity.
          ArticleModel model = new ArticleModel(article);

          // Then, we get all the Authors for this Article.
          // This will demo the QueryBuilder class.
          Dao<ArticleAuthor, Void> authorDao = helper
              .getArticleAuthorDao();
          PreparedQuery<ArticleAuthor> query = authorDao.queryBuilder()
              .where().eq(ArticleAuthor.ARTICLE_ID_COLUMN, articleId)
              .prepare();
          // Now, run the query
          List<ArticleAuthor> results = authorDao.query(query);
          for (ArticleAuthor item : results) {
            Author author = item.getAuthor();
            model.authorList.add(author);
          }

          // Finally, get a count of the comments. We will use
          // the QueryBuilder class again.
          Dao<Comment, Integer> commentDao = helper.getCommentDao();
          model.commentCount = countCommentsForId(commentDao, articleId);

          return model;
        }
      });
    }

    @Override
    protected void onPostExecute(ArticleModel articleModel) {
      done = true;
      payload = articleModel;
      host.handleDataLoaded(articleModel);
    }

    public void detach() {
      host = null;
    }

    public void attach(ArticleActivity newActivity) {
      host = newActivity;
      if (done)
        host.handleDataLoaded(payload);
    }
  }

  private static class AddCommentTask extends
      AsyncTask<Void, Void, Long> {
    private int articleId;
    private String name, comments;
    private ArticleActivity host;
    private DatabaseHelper helper;

    private AddCommentTask(ArticleActivity host, int articleId,
        String name, String comments) {
      this.host = host;
      this.articleId = articleId;
      this.comments = comments;
      this.name = name;
      helper = DatabaseHelper.getInstance(host);
    }

    @Override
    protected Long doInBackground(Void... voids) {
      // Note: The ORM only needs the ID set for foreign objects for it to
      // properly set the column value.
      Article article = new Article();
      article.setId(articleId);
      final Comment comment = new Comment(article, new Date(),
          comments, name);

      // Perform the insert. Then, get a new count.
      return helper.callInTransaction(new Callable<Long>() {
        @Override
        public Long call() throws SQLException {
          Dao<Comment, Integer> commentDao = helper.getCommentDao();
          commentDao.create(comment);
          return countCommentsForId(commentDao, articleId);
        }
      });
    }

    @Override
    protected void onPostExecute(Long count) {
      if (host != null)
        host.updateCommentCount(count);
    }

    public void detach() {
      host = null;
    }
  }

  /**
   * This function is used in multiple places, so we will define it once, as a
   * static method.
   * 
   * @param commentDao
   *          A Dao object for use when querying.
   * @param articleId
   *          The ID of the article.
   * @return A count of the comments associated with the given ID.
   * @throws SQLException
   */
  private static long countCommentsForId(
      Dao<Comment, Integer> commentDao, int articleId)
      throws SQLException {
    PreparedQuery<Comment> countQuery = commentDao.queryBuilder()
        .setCountOf(true).where().eq(Comment.ARTICLE_COLUMN, articleId)
        .prepare();
    return commentDao.countOf(countQuery);
  }
}
