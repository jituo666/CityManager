package com.xmkj.citymanager;

import com.xmkj.citymanager.util.GlobalPreference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class UserRegisterActivity extends Activity {

    private TextView mTitle;
    private View mExit;
    private EditText mUserName;
    private EditText mUserPwd;
    private Button mRegister;
    private TextView mUserRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_user_register);
        mTitle = (TextView)this.findViewById(R.id.navi_text);
        mTitle.setText(R.string.common_user_register);
        mExit = this.findViewById(R.id.action_navi);
        mExit.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUserName = (EditText) this.findViewById(R.id.user_name_input);
        mUserPwd = (EditText) this.findViewById(R.id.user_pwd_input);
        mRegister = (Button) this.findViewById(R.id.user_login);
        mRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mUserName.getText().toString().trim().length() != 11 || mUserPwd.getText().toString().trim().length() < 6) {
                    Toast.makeText(UserRegisterActivity.this, R.string.user_login_toast, Toast.LENGTH_SHORT).show();
                    return;
                }
                GlobalPreference.setUserName(UserRegisterActivity.this, mUserName.getText().toString().trim());
                GlobalPreference.setUserPwd(UserRegisterActivity.this, mUserPwd.getText().toString().trim());
                Intent it = new Intent(UserRegisterActivity.this, UserCenterActivity.class);
                startActivity(it);
                finish();
            }
        });

    }
}
