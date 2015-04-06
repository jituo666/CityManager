package com.xmkj.citymanager.news;

import java.util.List;

import com.xmkj.citymanager.R;
import com.xmkj.citymanager.news.NewDataManager.NewsData;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

    private List<NewsData> mDatas;
    private Activity mContext;

    public NewsAdapter(Activity cxt, List<NewsData> data) {
        mDatas = data;
        mContext = cxt;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
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

        return convertView;
    }

    private class ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView content;
    }
}
