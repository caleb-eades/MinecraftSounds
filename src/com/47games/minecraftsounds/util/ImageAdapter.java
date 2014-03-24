package com.foursevengames.minecraftsounds.util;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.GridView;
import android.view.ViewGroup.LayoutParams;
import android.media.SoundPool;
import android.util.TypedValue;

public class ImageAdapter extends BaseAdapter {
  private Context context;
  private Integer[] imageIds;

  public ImageAdapter(Context c) {
    context = c;
  }

  public int getCount() {
    return imageIds.length;
  }

  public Object getItem(int position) {
    return null;
  }

  public long getItemId(int position) {
    return 0;
  }

  // create a new ImageView for each item referenced by the Adapter
  public View getView(int position, View convertView, ViewGroup parent) {
    ImageView imageView;
    if (convertView == null) {  // if it's not recycled, initialize some attributes
      imageView = new ImageView(context);
      //convert to dp
      int jello = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 85, context.getResources().getDisplayMetrics());
      int pudding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics());
      imageView.setLayoutParams(new GridView.LayoutParams(jello, jello));
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      imageView.setPadding(pudding, pudding, pudding, pudding);
    } else {
      imageView = (ImageView) convertView;
    }

    imageView.setImageResource(imageIds[position]);
    return imageView;
  }

  public void giveImageIds(Integer[] imageIds) {
    this.imageIds = imageIds;
  }
}
