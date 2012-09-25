package com.manning.androidhacks.hack028.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.manning.androidhacks.hack028.R;

public class ImageAdapter extends BaseAdapter {
  private Context mContext;

  private Integer[] mImageIds = { R.drawable.sample_0,
      R.drawable.sample_1, R.drawable.sample_2, R.drawable.sample_3,
      R.drawable.sample_4, R.drawable.sample_5, R.drawable.sample_6,
      R.drawable.sample_7 };

  public ImageAdapter(Context c) {
    mContext = c;
  }

  public int getCount() {
    return mImageIds.length;
  }

  public Object getItem(int position) {
    return position;
  }

  public long getItemId(int position) {
    return position;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    ImageView imageView = new ImageView(mContext);

    imageView.setImageResource(mImageIds[position]);
    imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
    imageView.setScaleType(ImageView.ScaleType.FIT_XY);

    return imageView;
  }
}