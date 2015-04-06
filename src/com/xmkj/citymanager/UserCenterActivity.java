package com.xmkj.citymanager;

import com.xmkj.citymanager.util.GlobalPreference;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserCenterActivity extends Activity {

    private TextView mTitle;
    private View mExit;
    private TextView mUserName;
    private TextView mUserScore;
    private TextView mReportHistory;
    private TextView mSysMsg;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_user_center);

        mTitle = (TextView) this.findViewById(R.id.navi_text);
        mTitle.setText(R.string.bt_user_center);
        mExit = this.findViewById(R.id.action_navi);
        mExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //

        mReportHistory = (TextView) this.findViewById(R.id.user_msg_history);
        mReportHistory.setText(this.getString(R.string.user_report_history, GlobalPreference.getReportCount(this)));
        mSysMsg = (TextView) this.findViewById(R.id.user_msg_switch);
        mSysMsg.setText(this.getString(R.string.user_msg_switch, 0));
        //
        mUserName = (TextView) this.findViewById(R.id.user_name);
        mUserName.setText(getString(R.string.user_report_user, GlobalPreference.getUserName(this)));
        mUserScore = (TextView) this.findViewById(R.id.user_gender);
        mUserScore.setText(getString(R.string.user_report_score, GlobalPreference.getUserScore(this)));
        //

        mButton = (Button) this.findViewById(R.id.exit_login);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GlobalPreference.setUserName(UserCenterActivity.this, "");
                GlobalPreference.setUserPwd(UserCenterActivity.this, "");
                finish();
            }
        });
    }

}
