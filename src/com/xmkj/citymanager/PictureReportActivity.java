package com.xmkj.citymanager;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.xmkj.citymanager.util.PictureHorizontalScrollview;
import com.xmkj.citymanager.util.PictureListAdapter;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PictureReportActivity extends Activity {

    private TextView mTextViewTime;
    private TextView mTitle;
    private View mExit;
    private Button mUpload;

    PictureHorizontalScrollview mPictureListView;
    PictureListAdapter mPictureAdapter;
    private List<String> mPaths = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_report_view);
        mTitle = (TextView) this.findViewById(R.id.navi_text);
        mTitle.setText(R.string.bt_picture_report);
        mExit = this.findViewById(R.id.action_navi);
        mExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUpload = (Button)this.findViewById(R.id.ok_report);
        mUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mPaths.size() <=1) {
                    Toast.makeText(PictureReportActivity.this, R.string.picture_upload_tip_2, Toast.LENGTH_SHORT).show();
                    return;
                }
            }});
        mPaths.add("add");
        if (getIntent().getData() != null) {
            mPaths.add(0,getIntent().getData().toString());
        }
        mPictureListView = (PictureHorizontalScrollview) findViewById(R.id.scrollView);
        mPictureAdapter = new PictureListAdapter(this, R.layout.picture_list_item, mPaths);
        mPictureListView.setAdapter(this, mPictureAdapter);

        Log.i("xxxx", "--------=-----sxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxssss-----=--:" + mPaths + ":" + mPaths.size());
        //
        mTextViewTime = (TextView) this.findViewById(R.id.time_content);
        mTextViewTime.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm",Locale.CHINA).format(new Date(System.currentTimeMillis())));
        //

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.getResources().getStringArray(
                R.array.picture_type));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner s = (Spinner) findViewById(R.id.type_content);
        s.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri result = null;
        if (requestCode == MainActivity.TAKE_PHOTO && PictureDialog.sCurrentPhotoFile != null) {
            // 设置文件保存路径
            result = Uri.fromFile(PictureDialog.sCurrentPhotoFile);
        }
        // 读取相册缩放图片
        if (requestCode == MainActivity.PHOTO_ZOOM && data != null) {
            result = data.getData();
        }
        if (result != null) {
            mPaths.add(0,result.toString());
            mPictureAdapter = new PictureListAdapter(this, R.layout.picture_list_item, mPaths);
            mPictureListView.setAdapter(this, mPictureAdapter);
            Log.i("xxxx", "--------=-----sssss-----=--:" + mPaths + ":" + mPaths.size());
           // mPictureAdapter.notifyDataSetChanged();
        }
    }
}
