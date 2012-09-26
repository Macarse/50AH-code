/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack040;

import com.manning.androidhacks.hack040.provider.Images;
import com.manning.androidhacks.hack040.util.ImageCache;
import com.manning.androidhacks.hack040.util.ImageFetcher;
import com.manning.androidhacks.hack040.util.ImageWorker;
import com.manning.androidhacks.hack040.util.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * User: William Sanville Date: 7/30/12 Time: 10:09 AM Testing this
 * implementation with a list.
 */
public class TestListActivity extends Activity {
  private static final String IMAGE_CACHE_DIR = "thumbs";
  private ImageWorker imageWorker;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list);
    ListView list = (ListView) findViewById(R.id.list);

    int size = (int) getResources().getDimension(
        R.dimen.list_image_size);
    imageWorker = new ImageFetcher(this, size);
    imageWorker.setImageFadeIn(false);
    imageWorker.setLoadingImage(R.drawable.ic_launcher);

    ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(
        IMAGE_CACHE_DIR);

    // Allocate a third of the per-app memory limit to the bitmap memory cache.
    // This value
    // should be chosen carefully based on a number of factors. Refer to the
    // corresponding
    // Android Training class for more discussion:
    // http://developer.android.com/training/displaying-bitmaps/
    // In this case, we aren't using memory for much else other than this
    // activity and the
    // ImageDetailActivity so a third lets us keep all our sample image
    // thumbnails in memory
    // at once.
    cacheParams.memCacheSize = 1024 * 1024 * Utils.getMemoryClass(this) / 3;
    imageWorker.setImageCache(new ImageCache(this, cacheParams));

    list.setAdapter(new TestAdapter(this, imageWorker));
  }

  @Override
  protected void onPause() {
    super.onPause();
    imageWorker.setExitTasksEarly(true);
  }

  @Override
  protected void onResume() {
    super.onResume();
    imageWorker.setExitTasksEarly(false);
  }

  static class TestAdapter extends BaseAdapter {
    private Context context;
    private ImageWorker imageWorker;

    TestAdapter(Context context, ImageWorker imageWorker) {
      this.context = context;
      this.imageWorker = imageWorker;
    }

    @Override
    public int getCount() {
      return Images.otherUrlAdapter.getSize();
    }

    @Override
    public Object getItem(int i) {
      return Images.otherUrlAdapter.getItem(i);
    }

    @Override
    public long getItemId(int i) {
      return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
      if (view == null) {
        view = LayoutInflater.from(context).inflate(R.layout.list_item,
            null);
        view.setTag(new Holder(view));
      }

      Holder holder = (Holder) view.getTag();
      holder.textView.setText("Item " + i);
      imageWorker.loadImage(getItem(i), holder.imageView);

      return view;
    }

    static class Holder {
      ImageView imageView;
      TextView textView;

      Holder(View item) {
        imageView = (ImageView) item.findViewById(R.id.image);
        textView = (TextView) item.findViewById(R.id.text);
      }
    }
  }
}
