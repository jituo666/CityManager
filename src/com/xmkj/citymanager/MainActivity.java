package com.xmkj.citymanager;


import com.xmkj.citymanager.news.NewDataManager;
import com.xmkj.citymanager.news.NewsAdapter;
import com.xmkj.citymanager.util.GlobalPreference;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {

    protected static final int TAKE_PHOTO = 1;// 拍照
    protected static final int PHOTO_ZOOM = 2; // 缩放
    protected static final int PHOTO_RESOULT = 3;// 结果
    protected static final String IMAGE_UNSPECIFIED = "image/*";

    private ListView mNewsList;
    private ImageButton mPictureReport;
    private ImageButton mTextReport;
    private ImageButton mUserCommReport;
    private ImageButton mUserCenterReport;
    private View mBackView;
    //
    private NewsAdapter mNewsAdapter;

    private class NewsLoadingTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... params) {
            NewDataManager newManager = new NewDataManager(MainActivity.this);
            mNewsAdapter = new NewsAdapter(MainActivity.this, newManager.queryNews());
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
            mNewsList.setAdapter(mNewsAdapter);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsList = (ListView) this.findViewById(R.id.news_list);
        mNewsList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mPictureReport = (ImageButton) this.findViewById(R.id.picture_report);
        mTextReport = (ImageButton) this.findViewById(R.id.text_report);
        mUserCommReport = (ImageButton) this.findViewById(R.id.user_comm);
        mUserCenterReport = (ImageButton) this.findViewById(R.id.user_center);
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
        super.onActivityResult(requestCode, resultCode, data);
    }

}
