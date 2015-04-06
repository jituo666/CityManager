package com.xmkj.citymanager;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class TextReportActivity extends Activity {

    private TextView mTextViewTime;
    private TextView mTitle;
    private View mExit;

    private ArrayList<String> mPaths = new ArrayList<String>() {

        {
            add("test.png");
            add("add");
        }
    };

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
        Spinner typeSpinner = (Spinner) findViewById(R.id.issue_type_content);
        typeSpinner.setAdapter(typeAdapter);
        //
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.getResources().getStringArray(
                R.array.issue_area));
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner areaSpinner = (Spinner) findViewById(R.id.location_content);
        areaSpinner.setAdapter(areaAdapter);
    }
}
