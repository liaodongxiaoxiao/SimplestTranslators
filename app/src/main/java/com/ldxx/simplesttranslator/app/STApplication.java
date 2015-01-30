package com.ldxx.simplesttranslator.app;

import com.bmob.BmobConfiguration;
import com.bmob.BmobPro;
import com.ldxx.simplesttranslator.utils.AppUtils;
import com.lidroid.xutils.DbUtils;

import android.app.Application;

/**
 * 简译Application类
 */
public class STApplication extends Application {
    /**
     * 本地数据库名称
     */
    public static final String DBNAME = "st.db";

    /**
     * 首次进入系统的标识ͳ
     */
    //private boolean isFirstIn;

    /**
     * db操作对象
     */
    private DbUtils db;

    /**
     * 友盟App_Key
     */
    public static final String YOUMENG_APPKEY = "545adc1afd98c56ebf004cdc";

    /**
     * Bmob Application ID
     */
    public static final String BMOB_APPKEY = "c682d36b5358f46871eb8bf6d85c7b50";

    /**
     * 芒果移动广告 APP_KEY
     */
    public static final String MOGO_APPKEY = "29131a73de9c40859363b53f41962cae";

    /**
     * 百度 APP_KEY
     */
    public static final String BAIDU_APPKEY = "UE6gHRY2LL3pjXKe66YaIQqQ";

    @Override
    public void onCreate() {
        super.onCreate();
        db = DbUtils.create(this, DBNAME);
        AppUtils.application = this;

        //设置图片缓存路径
        BmobConfiguration config = new BmobConfiguration.Builder(this).customExternalCacheDir("pmg").build();
        BmobPro.getInstance(this).initConfig(config);
    }

    public DbUtils getDb() {
        return db;
    }
}
