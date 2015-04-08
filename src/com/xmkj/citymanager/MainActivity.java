
package com.xmkj.citymanager;

import java.util.List;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.xmkj.citymanager.news.NewDataManager;
import com.xmkj.citymanager.news.NewDataManager.NewsData;
import com.xmkj.citymanager.news.NewsAdapter;
import com.xmkj.citymanager.util.GlobalPreference;
import com.xmkj.citymanager.util.NewDataPrepare;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {

    private static final long TIM_INTERVAL = 10 * 60 * 1000;
    protected static final int TAKE_PHOTO = 1;// 拍照
    protected static final int PHOTO_ZOOM = 2; // 缩放
    protected static final int PHOTO_RESOULT = 3;// 结果
    protected static final String IMAGE_UNSPECIFIED = "image/*";

    private ListView mNewsList;
    private View mPictureReport;
    private View mTextReport;
    private View mUserCommReport;
    private View mUserCenterReport;
    private View mBackView;
    //
    private NewsAdapter mNewsAdapter;

    private class NewsLoadingTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;
        NewDataManager newManager = new NewDataManager(MainActivity.this);

        @Override
        protected Void doInBackground(Void... params) {

            AVQuery<AVObject> query = new AVQuery<AVObject>("NewData");
            try {
                List<NewsData> l = newManager.queryNews();
                if (l.size() == 0) {
                    List<AVObject> list = query.find();
                    newManager.addNews(list);
                } else {
                    if (System.currentTimeMillis() -GlobalPreference.getLastUpdateTime(MainActivity.this) > TIM_INTERVAL ) {
                        List<AVObject> list = query.whereGreaterThan("date", l.get(0).time).find();
                        newManager.addNews(list);
                        GlobalPreference.setLastUpdateTime(MainActivity.this, System.currentTimeMillis());
                    }
                }
            } catch (AVException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            List<NewsData> list = newManager.queryNews();
            Log.i("xxxx", "---------query av list:" + list.size());
            mNewsAdapter = new NewsAdapter(MainActivity.this, list);
            mNewsList.setAdapter(mNewsAdapter);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVAnalytics.trackAppOpened(getIntent());
        //
        // NewDataPrepare.prepare();
        //
        setContentView(R.layout.activity_main);
        mNewsList = (ListView) this.findViewById(R.id.news_list);
        mNewsList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent();
                it.setClass(MainActivity.this, NewsActivity.class);
                it.putExtra("title", mNewsAdapter.getItem(position).title);
                it.putExtra("content", mNewsAdapter.getItem(position).content);
                it.putExtra("url", mNewsAdapter.getItem(position).imageUrl);
                it.putExtra("author", mNewsAdapter.getItem(position).author);
                startActivity(it);
            }
        });
        mPictureReport = (View) this.findViewById(R.id.picture_report);
        mTextReport = (View) this.findViewById(R.id.text_report);
        mUserCommReport = (View) this.findViewById(R.id.user_comm);
        mUserCenterReport = (View) this.findViewById(R.id.user_center);
        mBackView = findViewById(R.id.action_navi);
        mPictureReport.setOnClickListener(this);
        mTextReport.setOnClickListener(this);
        mUserCommReport.setOnClickListener(this);
        mUserCenterReport.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        //
        new NewsLoadingTask().execute();
    }

    @Override
    public void onClick(View v) {
        if (v == mPictureReport) {
            PictureDialog dialog = new PictureDialog(this);
            dialog.show();
        } else if (v == mTextReport) {
            Intent it = new Intent(this, TextReportActivity.class);
            startActivity(it);
        } else if (v == mUserCommReport) {

        } else if (v == mUserCenterReport) {
            if (GlobalPreference.getUserName(this).length() > 0) {
                Intent it = new Intent(this, UserCenterActivity.class);
                startActivity(it);
            } else {
                Intent it = new Intent(this, UserLoginActivity.class);
                startActivity(it);
            }
        } else if (v == mBackView) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri result = null;
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TAKE_PHOTO && PictureDialog.sCurrentPhotoFile != null) {
                // 设置文件保存路径
                result = Uri.fromFile(PictureDialog.sCurrentPhotoFile);
            }
            // 读取相册缩放图片
            if (requestCode == PHOTO_ZOOM && data != null) {
                result = data.getData();
            }
            if (result != null) {
                Intent it = new Intent();
                it.setData(result);
                it.setClass(this, PictureReportActivity.class);
                startActivity(it);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
