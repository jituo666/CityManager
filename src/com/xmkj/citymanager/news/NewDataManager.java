package com.xmkj.citymanager.news;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class NewDataManager {

    public static final String DB_TABLE_NAME = "tb_news";
    public final static String TABLE_COLUMN_NEWS_TITLE = "title";
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
        mSQLiteDatabase.query(DB_TABLE_NAME, null, null, null, null, null, " time DESC");
        return news;
    }

    public void addNews(List<NewsData> news) {
        mSQLiteDatabase.beginTransaction();
        for (NewsData data : news) {
            ContentValues values = new ContentValues();
            values.put(TABLE_COLUMN_NEWS_TITLE, data.title);
            values.put(TABLE_COLUMN_NEWS_CONTENT, data.content);
            values.put(TABLE_COLUMN_NEWS_TIME, data.time);
            values.put(TABLE_COLUMN_IMAGE_URL, data.imageUrl);
            mSQLiteDatabase.insert(DB_TABLE_NAME, null, values);
        }
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();
    }

    public static class NewsData {
        String title;
        String content;
        String time;
        String imageUrl;
    }
}
