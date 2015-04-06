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

public class UserLoginActivity extends Activity {

    private TextView mTitle;
    private View mExit;
    private EditText mUserName;
    private EditText mUserPwd;
    private Button mLogin;
    private TextView mUserRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_user_login);
        mTitle = (TextView)this.findViewById(R.id.navi_text);
        mTitle.setText(R.string.common_user_login);
        mExit = this.findViewById(R.id.action_navi);
        mExit.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUserName = (EditText) this.findViewById(R.id.user_name_input);
        mUserPwd = (EditText) this.findViewById(R.id.user_pwd_input);
        mLogin = (Button) this.findViewById(R.id.user_login);
        mLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mUserName.getText().toString().trim().length() != 11 || mUserPwd.getText().toString().trim().length() < 6) {
                    Toast.makeText(UserLoginActivity.this, R.string.user_login_toast, Toast.LENGTH_SHORT).show();
                    return;
                }
                GlobalPreference.setUserName(UserLoginActivity.this, mUserName.getText().toString().trim());
                GlobalPreference.setUserPwd(UserLoginActivity.this, mUserPwd.getText().toString().trim());
                Intent it = new Intent(UserLoginActivity.this, UserCenterActivity.class);
                startActivity(it);
                finish();
            }
        });
        //
        mUserRegister = (TextView)this.findViewById(R.id.user_register);
        mUserRegister.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent it = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

}
