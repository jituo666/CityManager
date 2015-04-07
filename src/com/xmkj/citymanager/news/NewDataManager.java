package com.xmkj.citymanager.news;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NewDataManager {

    public static final String DB_TABLE_NAME = "tb_news";
    public final static String TABLE_COLUMN_NEWS_TITLE = "title";
    public final static String TABLE_COLUMN_NEWS_AUTHOR = "author";
    public final static String TABLE_COLUMN_NEWS_CONTENT = "content";
    public final static String TABLE_COLUMN_NEWS_TIME = "time";
    public final static String TABLE_COLUMN_IMAGE_URL = "imageUrl";

    private SQLiteDatabase mSQLiteDatabase;

    public NewDataManager(Context cxt) {
        NewsDBHelper dbHelper = new NewsDBHelper(cxt);
        mSQLiteDatabase = dbHelper.getWritableDatabase();
    }

    public List<NewsData> queryNews() {
        List<NewsData> news = new ArrayList<NewsData>();
        Cursor c = mSQLiteDatabase.query(DB_TABLE_NAME, null, null, null, null, null, " time DESC");
        if (c == null)
            return news;
        if (c.moveToFirst()) {
            Log.i("xxx", c.getCount() + "--------------:c c:" + c.getColumnCount() + ":" + c.getColumnIndex(TABLE_COLUMN_NEWS_TITLE));
            do {
                NewsData d = new NewsData();
                d.title = c.getString(c.getColumnIndex(TABLE_COLUMN_NEWS_TITLE));
                d.author = c.getString(c.getColumnIndex(TABLE_COLUMN_NEWS_AUTHOR));
                d.content = c.getString(c.getColumnIndex(TABLE_COLUMN_NEWS_CONTENT));
                d.imageUrl = c.getString(c.getColumnIndex(TABLE_COLUMN_IMAGE_URL));
                d.time = c.getString(c.getColumnIndex(TABLE_COLUMN_NEWS_TIME));
                news.add(d);
            } while (c.moveToNext());
        }
        c.close();
        return news;
    }

    //    public void addNews(List<NewsData> news) {
    //        mSQLiteDatabase.beginTransaction();
    //        for (NewsData data : news) {
    //            ContentValues values = new ContentValues();
    //            values.put(TABLE_COLUMN_NEWS_TITLE, data.title);
    //            values.put(TABLE_COLUMN_NEWS_CONTENT, data.content);
    //            values.put(TABLE_COLUMN_NEWS_TIME, data.time);
    //            values.put(TABLE_COLUMN_IMAGE_URL, data.imageUrl);
    //            mSQLiteDatabase.insert(DB_TABLE_NAME, null, values);
    //        }
    //        mSQLiteDatabase.setTransactionSuccessful();
    //        mSQLiteDatabase.endTransaction();
    //    }

    public void addNews(List<AVObject> news) {
        mSQLiteDatabase.beginTransaction();
        for (AVObject data : news) {
            ContentValues values = new ContentValues();
            values.put(TABLE_COLUMN_NEWS_TITLE, data.getString("title"));
            values.put(TABLE_COLUMN_NEWS_AUTHOR, data.getString("author") + "  " + data.getString("date"));
            values.put(TABLE_COLUMN_NEWS_CONTENT, data.getString("content"));
            values.put(TABLE_COLUMN_NEWS_TIME, data.getString("date"));
            values.put(TABLE_COLUMN_IMAGE_URL, data.getAVFile("news_image").getUrl());
            mSQLiteDatabase.insert(DB_TABLE_NAME, null, values);
        }
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();
    }

    public static class NewsData {

        public String title;
        public String content;
        public String time;
        public String author;
        public String imageUrl;
    }
}
