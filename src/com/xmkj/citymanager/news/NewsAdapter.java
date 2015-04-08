package com.xmkj.citymanager.news;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xmkj.citymanager.R;
import com.xmkj.citymanager.news.NewDataManager.NewsData;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    DisplayImageOptions options;
    private List<NewsData> mDatas;
    private Activity mContext;

    public NewsAdapter(Activity cxt, List<NewsData> data) {
        mDatas = data;
        mContext = cxt;
        options = new DisplayImageOptions.Builder()
        .showImageOnLoading(R.drawable.default_bg)
        .showImageForEmptyUri(R.drawable.default_bg)
        .showImageOnFail(R.drawable.default_bg)
        .cacheInMemory(true)
        .cacheOnDisc(true)
        .considerExifParams(true)
        .displayer(new RoundedBitmapDisplayer(0))
        .build();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public NewsData getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mContext.getLayoutInflater().inflate(R.layout.common_list_item, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.title = (TextView) convertView.findViewById(R.id.text_title);
            holder.content = (TextView) convertView.findViewById(R.id.text_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NewsData data = getItem(position);
        imageLoader.displayImage(data.imageUrl, holder.image, options, animateFirstListener);
        holder.title.setText(data.title);
        holder.content.setText(data.content);
        return convertView;
    }

    private class ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView content;
    }
    
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
