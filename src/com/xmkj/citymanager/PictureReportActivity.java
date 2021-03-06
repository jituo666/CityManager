package com.xmkj.citymanager;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.xmkj.citymanager.util.PictureHorizontalScrollview;
import com.xmkj.citymanager.util.PictureListAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PictureReportActivity extends Activity {

    private TextView mTextViewTime;
    private TextView mPlaceView;
    private TextView mTitle;
    private EditText mDesc;
    private Spinner mSpinner;
    private View mExit;
    
    private Button mUpload;

    PictureHorizontalScrollview mPictureListView;
    PictureListAdapter mPictureAdapter;
    private List<String> mPaths = new ArrayList<String>();

    private class PictureUploadTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... params) {

            AVObject pictureReport = new AVObject("PictureReports");
            pictureReport.put("time", mTextViewTime.getText());
            pictureReport.put("location", mPlaceView.getText());
            pictureReport.put("type", mSpinner.getSelectedItem().toString());
            pictureReport.put("desc", mDesc.getText().toString());
            try {
                pictureReport.save();
            } catch (AVException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PictureReportActivity.this);
            progressDialog.setMessage(getString(R.string.picture_uploading));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                Toast.makeText(PictureReportActivity.this, R.string.picture_upload_success, Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_report_view);
        mTitle = (TextView) this.findViewById(R.id.navi_text);
        mTitle.setText(R.string.bt_picture_report);
        mSpinner = (Spinner)this.findViewById(R.id.type_content);
        mExit = this.findViewById(R.id.action_navi);
        mExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUpload = (Button) this.findViewById(R.id.ok_report);
        mUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mPaths.size() <= 1) {
                    Toast.makeText(PictureReportActivity.this, R.string.picture_upload_tip_2, Toast.LENGTH_SHORT).show();
                    return;
                }
                new PictureUploadTask().execute();
            }
        });
        mPaths.add("add");
        if (getIntent().getData() != null) {
            mPaths.add(0, getIntent().getData().toString());
        }
        mPictureListView = (PictureHorizontalScrollview) findViewById(R.id.scrollView);
        mPictureAdapter = new PictureListAdapter(this, R.layout.picture_list_item, mPaths);
        mPictureListView.setAdapter(this, mPictureAdapter);

        Log.i("xxxx", "--------=-----sxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxssss-----=--:" + mPaths + ":" + mPaths.size());
        //
        mTextViewTime = (TextView) this.findViewById(R.id.time_content);
        mTextViewTime.setText(new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CHINA).format(new Date(System.currentTimeMillis())));
        //

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.getResources().getStringArray(
                R.array.picture_type));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner s = (Spinner) findViewById(R.id.type_content);
        s.setAdapter(adapter);
        //
        mPlaceView = (TextView) this.findViewById(R.id.location_content);
        LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (loc != null) {
            mPlaceView.setText(getString(R.string.user_place, String.valueOf(loc.getLongitude()), String.valueOf(loc.getLatitude())));
        }
        mDesc = (EditText)this.findViewById(R.id.desc_content);
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
            mPaths.add(0, result.toString());
            mPictureAdapter = new PictureListAdapter(this, R.layout.picture_list_item, mPaths);
            mPictureListView.setAdapter(this, mPictureAdapter);
            Log.i("xxxx", "--------=-----sssss-----=--:" + mPaths + ":" + mPaths.size());
            // mPictureAdapter.notifyDataSetChanged();
        }
    }
}
