package com.ldxx.simplesttranslator.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ldxx.simplesttranslator.R;
import com.ldxx.simplesttranslator.app.BaseActivity;
import com.ldxx.simplesttranslator.app.STApplication;
import com.ldxx.simplesttranslator.bean.UserInfo;
import com.ldxx.utils.StringUtils;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class SignInActivity extends BaseActivity {

    private EditText user_name;

    private EditText password;

    private EditText rePass;

    private EditText email;

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        user_name = (EditText) findViewById(R.id.user_name);
        user_name.addTextChangedListener(new EditTextWatcher(true));
        password = (EditText) findViewById(R.id.password);
        rePass = (EditText) findViewById(R.id.repass);
        email = (EditText) findViewById(R.id.email);
        email.addTextChangedListener(new EditTextWatcher(false));
        // 初始化 Bmob SDK
        Bmob.initialize(this, STApplication.BMOB_APPKEY);

        initToolBar(R.string.title_activity_sign_in);
        //初始化ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public void toLogin(View view) {
        String us = user_name.getText().toString();
        String ps = password.getText().toString();
        String rs = rePass.getText().toString();
        String es = email.getText().toString();

        if (StringUtils.isEmpty(us) || StringUtils.isEmpty(ps)
                || StringUtils.isEmpty(rs) || StringUtils.isEmpty(es)) {
            Toast.makeText(this, "请将基本信息填写完整", Toast.LENGTH_SHORT).show();
        } else if (!ps.equals(rs)) {
            Toast.makeText(this, "两次填写的密码不一致", Toast.LENGTH_SHORT).show();
            rePass.setText("");
        } else {
            //用户名密码合法
           final ProgressDialog   progressDialog = ProgressDialog.show(this, "", "注册中，请稍等...", true, false);
            final UserInfo user = new UserInfo();
            user.setUsername(us);
            user.setPassword(ps);
            user.setEmail(es);
            //user.setEmailVerified(true);

            user.signUp(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    progressDialog.cancel();
                    Log.e(TAG, "注册成功:");
                    Toast.makeText(SignInActivity.this, "注册成功，请登录注册邮箱，完成用户激活。", Toast.LENGTH_LONG).show();
                    //BmobUser user = BmobUser.getCurrentUser(RegisterActivity.this);
                    //if(user!=null){
                    //    Log.e(TAG,user.getUsername());
                    //}
                    startActivity(new Intent(SignInActivity.this, LoginActivity.class));
                    finish();
                }

                @Override
                public void onFailure(int code, String msg) {
                    progressDialog.cancel();
                    Toast.makeText(SignInActivity.this, "注册失败:" +msg, Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }

    }

    class EditTextWatcher implements TextWatcher {
        private boolean isUserName;

        public EditTextWatcher(boolean isUserName) {
            this.isUserName = isUserName;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            BmobQuery<BmobUser> query = new BmobQuery<>();
            //设置用户名
            if (isUserName) {
                query.addWhereEqualTo("username", user_name.getText());
            } else {
                query.addWhereEqualTo("email", email.getText());
            }

            query.findObjects(SignInActivity.this, new FindListener<BmobUser>() {
                @Override
                public void onSuccess(List<BmobUser> mobUsers) {
                    if (mobUsers != null && !mobUsers.isEmpty()) {
                        String msg = isUserName ? "用户名" : "邮箱";
                        Toast.makeText(SignInActivity.this, msg + "已存在", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }
    }
}
