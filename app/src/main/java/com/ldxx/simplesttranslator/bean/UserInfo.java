package com.ldxx.simplesttranslator.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by wangzhuo-neu on 2015/1/29.
 */
public class UserInfo extends BmobUser {
    private String photo_url;

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
