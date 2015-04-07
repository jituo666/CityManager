package com.xmkj.citymanager.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.xmkj.citymanager.PictureDialog;
import com.xmkj.citymanager.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class PictureListAdapter extends BaseAdapter {

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    DisplayImageOptions options;
    private Activity context;
    private List<String> list;
    private int layoutId;
    private Holder holder;
    public int currPosition = 0;

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutId, null);
            holder = new Holder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_pciture);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final String newsSource = (String) getItem(position);
        if (newsSource.equals("add")) {
            holder.imageView.setImageResource(R.drawable.ic_action_new);
        } else {
            imageLoader.displayImage(newsSource, holder.imageView, options, animateFirstListener);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (newsSource.equals("add")) {
                    if (list.size() >= 4) {
                        Toast.makeText(context, R.string.picture_upload_tip_1, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final PictureDialog pd = new PictureDialog(context);
                    pd.show();
                } else {

                }
            }
        });
        return convertView;
    }

    //
    public PictureListAdapter(Activity a, int textViewResourceId, List<String> l) {
        context = a;
        layoutId = textViewResourceId;
        list = l;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(0))
                .build();
    }

    //
    private class Holder {

        public ImageView imageView;

    }

    //
    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

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
