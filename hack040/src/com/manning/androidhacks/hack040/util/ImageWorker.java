/*
s * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.manning.androidhacks.hack040.util;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.manning.androidhacks.hack040.BuildConfig;

/**
 * This class wraps up completing some arbitrary long running work when loading
 * a bitmap to an ImageView. It handles things like using a memory and disk
 * cache, running the work in a background thread and setting a placeholder
 * image.
 */
public abstract class ImageWorker {
  private static final String TAG = "ImageWorker";

  protected ImageCache mImageCache;
  private Bitmap mLoadingBitmap;
  private boolean mFadeInBitmap = true;
  private volatile boolean mExitTasksEarly = false;

  protected Activity mActivity;
  protected ImageWorkerAdapter mImageWorkerAdapter;

  protected ImageWorker(Activity activity) {
    mActivity = activity;
  }

  /**
   * Load an image specified by the data parameter into an ImageView (override
   * {@link ImageWorker#processBitmap(Object)} to define the processing logic).
   * A memory and disk cache will be used if an {@link ImageCache} has been set
   * using {@link ImageWorker#setImageCache(ImageCache)}. If the image is found
   * in the memory cache, it is set immediately, otherwise an {@link AsyncTask}
   * will be created to asynchronously load the bitmap.
   * 
   * @param data
   *          The URL of the image to download.
   * @param imageView
   *          The ImageView to bind the downloaded image to.
   */
  public void loadImage(Object data, ImageView imageView) {
    Bitmap bitmap = null;

    if (mImageCache != null) {
      bitmap = mImageCache.getBitmapFromMemCache(String.valueOf(data));
    }

    if (bitmap != null) {
      // Bitmap found in memory cache
      imageView.setImageBitmap(bitmap);
    } else if (cancelPotentialWork(data, imageView)) {
      final BitmapWorkerTask task = makeTask(imageView, data);
      final AsyncDrawable asyncDrawable = new AsyncDrawable(
          mActivity.getResources(), mLoadingBitmap, task);
      imageView.setImageDrawable(asyncDrawable);
      NetworkThreadPool.submitTask(task);
    }
  }

  /**
   * Load an image specified from a set adapter into an ImageView (override
   * {@link ImageWorker#processBitmap(Object)} to define the processing logic).
   * A memory and disk cache will be used if an {@link ImageCache} has been set
   * using {@link ImageWorker#setImageCache(ImageCache)}. If the image is found
   * in the memory cache, it is set immediately, otherwise an {@link AsyncTask}
   * will be created to asynchronously load the bitmap.
   * {@link ImageWorker#setAdapter(ImageWorkerAdapter)} must be called before
   * using this method.
   * 
   * @param num
   *          The URL of the image to download.
   * @param imageView
   *          The ImageView to bind the downloaded image to.
   */
  public void loadImage(int num, ImageView imageView) {
    if (mImageWorkerAdapter != null) {
      loadImage(mImageWorkerAdapter.getItem(num), imageView);
    } else {
      throw new NullPointerException(
          "Data not set, must call setAdapter() first.");
    }
  }

  /**
   * Set placeholder bitmap that shows when the the background thread is
   * running.
   * 
   * @param bitmap
   */
  public void setLoadingImage(Bitmap bitmap) {
    mLoadingBitmap = bitmap;
  }

  /**
   * Set placeholder bitmap that shows when the the background thread is
   * running.
   * 
   * @param resId
   */
  public void setLoadingImage(int resId) {
    mLoadingBitmap = BitmapFactory.decodeResource(
        mActivity.getResources(), resId);
  }

  public Bitmap getLoadingBitmap() {
    return mLoadingBitmap;
  }

  /**
   * Set the {@link ImageCache} object to use with this ImageWorker.
   * 
   * @param cacheCallback
   */
  public void setImageCache(ImageCache cacheCallback) {
    mImageCache = cacheCallback;
  }

  public ImageCache getImageCache() {
    return mImageCache;
  }

  /**
   * If set to true, the image will fade-in once it has been loaded by the
   * background thread.
   * 
   * @param fadeIn
   */
  public void setImageFadeIn(boolean fadeIn) {
    mFadeInBitmap = fadeIn;
  }

  public boolean isFadeInBitmap() {
    return mFadeInBitmap;
  }

  public void setExitTasksEarly(boolean exitTasksEarly) {
    mExitTasksEarly = exitTasksEarly;
  }

  /**
   * Set the simple adapter which holds the backing data.
   * 
   * @param adapter
   */
  public void setAdapter(ImageWorkerAdapter adapter) {
    mImageWorkerAdapter = adapter;
  }

  /**
   * Get the current adapter.
   * 
   * @return
   */
  public ImageWorkerAdapter getAdapter() {
    return mImageWorkerAdapter;
  }

  /**
   * Subclasses should override this to define any processing or work that must
   * happen to produce the final bitmap. This will be executed in a background
   * thread and be long running. For example, you could resize a large bitmap
   * here, or pull down an image from the network.
   * 
   * @param data
   *          The data to identify which image to process
   * @return The processed bitmap
   */
  protected abstract Bitmap processBitmap(Object data);

  public static void cancelWork(ImageView imageView) {
    final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
    if (bitmapWorkerTask != null) {
      bitmapWorkerTask.cancel(true);
      if (BuildConfig.DEBUG) {
        final Object bitmapData = bitmapWorkerTask.data;
        Log.d(TAG, "cancelWork - cancelled work for " + bitmapData);
      }
    }
  }

  /**
   * Returns true if the current work has been canceled or if there was no work
   * in progress on this image view. Returns false if the work in progress deals
   * with the same data. The work is not stopped in that case.
   */
  public static boolean cancelPotentialWork(Object data,
      ImageView imageView) {
    final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

    if (bitmapWorkerTask != null) {
      final Object bitmapData = bitmapWorkerTask.data;
      if (bitmapData == null || !bitmapData.equals(data)) {
        bitmapWorkerTask.cancel(false); // we will allow currently running tasks
                                        // to continue
        if (BuildConfig.DEBUG) {
          Log.d(TAG, "cancelPotentialWork - cancelled work for " + data);
        }
      } else {
        // The same work is already in progress.
        return false;
      }
    }
    return true;
  }

  /**
   * @param imageView
   *          Any imageView
   * @return Retrieve the currently active work task (if any) associated with
   *         this imageView. null if there is no such task.
   */
  static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
    if (imageView != null) {
      final Drawable drawable = imageView.getDrawable();
      if (drawable instanceof AsyncDrawable) {
        final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
        return asyncDrawable.getBitmapWorkerTask();
      }
    }
    return null;
  }

  public static boolean isValidData(Object data, ImageView imageView) {
    BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
    return bitmapWorkerTask != null
        && bitmapWorkerTask.isSameData(data);
  }

  /**
   * Static creator method for setting up a LIFOTask with a Runnable inside.
   * 
   * @param imageView
   *          The ImageView to use when displaying the loaded image.
   * @param data
   *          The URL to load.
   * @return A fully baked LIFOTask.
   */
  private BitmapWorkerTask makeTask(ImageView imageView, Object data) {
    BitmapWorkerRunnable runnable = new BitmapWorkerRunnable(imageView,
        data);
    BitmapWorkerTask task = new BitmapWorkerTask(runnable, data);
    runnable.setParentTask(task);
    return task;
  }

  /**
   * A FutureTask that is used for the underlying priority queue for
   * implementing LIFO image loading.
   */
  private class BitmapWorkerTask extends LIFOTask {
    Object data;

    private BitmapWorkerTask(BitmapWorkerRunnable runnable, Object data) {
      super(runnable);
      this.data = data;
    }

    public boolean isSameData(Object data) {
      return this.data.equals(data);
    }
  }

  /**
   * The actual Runnable that will process the image.
   */
  private class BitmapWorkerRunnable implements Runnable {
    private Object data;
    private final WeakReference<ImageView> imageViewReference;
    private BitmapWorkerTask parentTask;

    private BitmapWorkerRunnable(ImageView imageView, Object data) {
      imageViewReference = new WeakReference<ImageView>(imageView);
      this.data = data;
    }

    public BitmapWorkerTask getParentTask() {
      return parentTask;
    }

    public void setParentTask(BitmapWorkerTask parentTask) {
      this.parentTask = parentTask;
    }

    private boolean isCancelled() {
      return parentTask == null || parentTask.isCancelled();
    }

    /**
     * Actual processing. This is assumed to be called from a thread pool or
     * something outside the UI thread.
     */
    @Override
    public void run() {
      final String dataString = String.valueOf(data);
      Bitmap bitmap = null;

      // If the image cache is available and this task has not been cancelled by
      // another
      // thread and the ImageView that was originally bound to this task is
      // still bound back
      // to this task and our "exit early" flag is not set then try and fetch
      // the bitmap from
      // the cache
      if (mImageCache != null && !isCancelled()
          && getAttachedImageView() != null && !mExitTasksEarly) {
        bitmap = mImageCache.getBitmapFromDiskCache(dataString);
      }

      // If the bitmap was not found in the cache and this task has not been
      // cancelled by
      // another thread and the ImageView that was originally bound to this task
      // is still
      // bound back to this task and our "exit early" flag is not set, then call
      // the main
      // process method (as implemented by a subclass)
      if (bitmap == null && !isCancelled()
          && getAttachedImageView() != null && !mExitTasksEarly) {
        bitmap = processBitmap(data);
      }

      // If the bitmap was processed and the image cache is available, then add
      // the processed
      // bitmap to the cache for future use. Note we don't check if the task was
      // cancelled
      // here, if it was, and the thread is still running, we may as well add
      // the processed
      // bitmap to our cache as it might be used again in the future
      if (bitmap != null && mImageCache != null) {
        mImageCache.addBitmapToCache(dataString, bitmap);
      }

      if (isCancelled() || mExitTasksEarly) {
        bitmap = null;
      }

      if (bitmap != null) {
        BitmapSetter runnable;
        if (mFadeInBitmap)
          runnable = new FadeInBitmapSetter(data, bitmap,
              getAttachedImageView(), mLoadingBitmap);
        else
          runnable = new BitmapSetter(data, bitmap,
              getAttachedImageView());

        mActivity.runOnUiThread(runnable);
      }
    }

    /**
     * Returns the ImageView associated with this task as long as the
     * ImageView's task still points to this task as well. Returns null
     * otherwise.
     */
    private ImageView getAttachedImageView() {
      final ImageView imageView = imageViewReference.get();
      final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

      if (getParentTask() == bitmapWorkerTask) {
        return imageView;
      }

      return null;
    }
  }

  /**
   * A very simple adapter for use with ImageWorker class and subclasses.
   */
  private static class AsyncDrawable extends BitmapDrawable {
    private BitmapWorkerTask bitmapWorkerTask;

    public AsyncDrawable(Resources res, Bitmap bitmap,
        BitmapWorkerTask bitmapWorkerTask) {
      super(res, bitmap);

      this.bitmapWorkerTask = bitmapWorkerTask;
    }

    public BitmapWorkerTask getBitmapWorkerTask() {
      return bitmapWorkerTask;
    }
  }

  private static class BitmapSetter implements Runnable {
    protected Object data;
    protected Bitmap bitmap;
    protected ImageView imageView;

    private BitmapSetter(Object data, Bitmap bitmap, ImageView imageView) {
      this.data = data;
      this.bitmap = bitmap;
      this.imageView = imageView;
    }

    @Override
    public void run() {
      if (imageView != null && bitmap != null && data != null) {
        if (isValidData(data, imageView)) {
          setImageBitmap();
        }
      }
      imageView = null;
      bitmap = null;
      data = null;
    }

    protected void setImageBitmap() {
      imageView.setImageBitmap(bitmap);
    }
  }

  private static class FadeInBitmapSetter extends BitmapSetter {
    private static final int FADE_IN_TIME = 200;
    protected Bitmap loadingBitmap;

    private FadeInBitmapSetter(Object data, Bitmap bitmap,
        ImageView imageView, Bitmap loadingBitmap) {
      super(data, bitmap, imageView);
      this.loadingBitmap = loadingBitmap;
    }

    @Override
    protected void setImageBitmap() {
      Resources resources = imageView.getContext().getResources();
      // Transition drawable with a transparent drawable and the final bitmap
      TransitionDrawable td = new TransitionDrawable(new Drawable[] {
          new ColorDrawable(android.R.color.transparent),
          new BitmapDrawable(resources, bitmap) });
      // Set background to loading bitmap
      imageView.setBackgroundDrawable(new BitmapDrawable(resources,
          loadingBitmap));

      imageView.setImageDrawable(td);
      td.startTransition(FADE_IN_TIME);
    }
  }

  /**
   * A very simple adapter for use with ImageWorker class and subclasses.
   */
  public static abstract class ImageWorkerAdapter {
    public abstract Object getItem(int num);

    public abstract int getSize();
  }
}
