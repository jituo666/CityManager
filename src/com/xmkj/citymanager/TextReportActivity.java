package com.xmkj.citymanager;


import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TextReportActivity extends Activity {


    private Spinner mSpinnerType;
    private Spinner mSpinnerArea;
    private TextView mTitle;
    private EditText mPhoneNumber;
    private EditText mTextContent;
    private TextView mPlaceView;
    private View mExit;
    private Button mUpload;


    private class TextReportUploadTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... params) {

            AVObject pictureReport = new AVObject("TextReports");
            pictureReport.put("type", mSpinnerType.getSelectedItem().toString());
            pictureReport.put("area", mSpinnerArea.getSelectedItem().toString());
            pictureReport.put("location", mPlaceView.getText());
            pictureReport.put("phoneNumber", mPhoneNumber.getText());
            pictureReport.put("textContent", mTextContent.getText().toString());
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
            progressDialog = new ProgressDialog(TextReportActivity.this);
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
                Toast.makeText(TextReportActivity.this, R.string.picture_upload_success, Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_report_view);

        mTitle = (TextView) this.findViewById(R.id.navi_text);
        mTitle.setText(R.string.bt_text_report);
        mExit = this.findViewById(R.id.action_navi);
        mExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ;
        //
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.getResources().getStringArray(
                R.array.issue_type));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerType = (Spinner) findViewById(R.id.issue_type_content);
        mSpinnerType.setAdapter(typeAdapter);
        //
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.getResources().getStringArray(
                R.array.issue_area));
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerArea = (Spinner) findViewById(R.id.location_content);
        mSpinnerArea.setAdapter(areaAdapter);
        //
        mPlaceView = (TextView)this.findViewById(R.id.place_content);
        LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (loc != null) {
            mPlaceView.setText(getString(R.string.user_place,String.valueOf(loc.getLongitude()),String.valueOf(loc.getLatitude())));
        }
        mUpload = (Button) this.findViewById(R.id.ok_report);
        mUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mTextContent.getText().length() == 0) {
                    Toast.makeText(TextReportActivity.this, R.string.picture_upload_tip_2, Toast.LENGTH_SHORT).show();
                    return;
                }
                new TextReportUploadTask().execute();
            }
        });

        mPhoneNumber = (EditText)this.findViewById(R.id.report_phone_content);
        mTextContent = (EditText)this.findViewById(R.id.desc_content);
        //
    }
}
