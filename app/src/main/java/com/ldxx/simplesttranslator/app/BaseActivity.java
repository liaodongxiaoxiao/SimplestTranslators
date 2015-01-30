package com.ldxx.simplesttranslator.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.ldxx.simplesttranslator.R;

/**
 * Created by wangzhuo-neu on 2015/1/29.
 */
public class BaseActivity extends ActionBarActivity {
    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Toolbar initToolBar(int res){
        //设置Actionbar
        Toolbar   toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.setTitle(res);// 标题的文字需在setSupportActionBar之前，不然会无效
        // toolbar.setSubtitle("副标题");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return  toolbar;
    }
}
