package com.ldxx.simplesttranslator.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ldxx.simplesttranslator.R;
import com.ldxx.simplesttranslator.app.BaseActivity;
import com.ldxx.simplesttranslator.app.STApplication;
import com.ldxx.utils.StringUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {

    private TextView usernameTV;
    private TextView passwordTV;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //取消登录进度条
            progressDialog.cancel();
            switch (msg.what) {
                //登录成功
                case 1:
                    BmobUser user = BmobUser.getCurrentUser(LoginActivity.this);
                    //完成邮件激活
                    if (user!=null&&user.getEmailVerified()!=null&&user.getEmailVerified()) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        //登录成功跳转到主页面
                        //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "请登录注册邮箱完成账号激活", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case -1:
                    Bundle bundle = msg.getData();
                    int code = bundle.getInt("code");
                    String ms;
                    //用户名或密码错误
                    if (code == 101) {
                        ms = "用户名或密码错误，请重试";
                    } else if (code == 9016) {
                        ms = "网络连接失败，请检查网络";
                    }else if(code ==9010){
                        ms ="网络异常，连接超时";
                    } else {
                        ms = "登录失败，请稍后重试";
                    }
                    Toast.makeText(LoginActivity.this, ms, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameTV = (TextView) findViewById(R.id.login_username);
        passwordTV = (TextView) findViewById(R.id.login_password);

        // 初始化 Bmob SDK
        Bmob.initialize(this, STApplication.BMOB_APPKEY);
        initToolBar(R.string.title_activity_login);
    }

    /**
     * 登录方法
     *
     * @param view 登录Button
     */
    public void onLogin(View view) {

        String userName = usernameTV.getText().toString();
        String passWord = passwordTV.getText().toString();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            //用户名密码合法
            progressDialog = ProgressDialog.show(LoginActivity
                    .this, "", "拼命登录中，请稍等...", true, false);
            BmobUser user = new BmobUser();
            user.setUsername(userName);
            user.setPassword(passWord);
            final Message message = handler.obtainMessage();

            user.login(this, new SaveListener() {
                @Override
                public void onSuccess() {

                    message.what = 1;
                    handler.sendMessage(message);
                }

                @Override
                public void onFailure(int code, String msg) {
                    //Toast.makeText(LoginActivity.this, "登录失败:" + code + "-" + msg, Toast.LENGTH_SHORT).show();
                    Log.e("登录", "登录失败:" + code + ":" + msg);
                    message.what = -1;
                    Bundle bundle = new Bundle();
                    bundle.putInt("code", code);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            });

        }
    }

    /**
     * 注册方法
     *
     * @param view 注册Button
     */
    public void onRegister(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
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
}
