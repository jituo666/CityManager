package com.xmkj.citymanager.news;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsDBHelper extends SQLiteOpenHelper {

    public final static String SQLITE_NAME = "news.db";
    public final static int NEWS_DB_VERSION = 1;

    public NewsDBHelper(Context context) {
        super(context, SQLITE_NAME, null, NEWS_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists tb_news (_id INTEGER primary key autoincrement, title TEXT, content TEXT, time TEXT,author TEXT,imageUrl TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("drop table if exists tb_news");
        onCreate(db);
    }
}
