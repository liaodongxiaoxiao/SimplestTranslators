package com.ldxx.simplesttranslator.bean;

import android.support.v4.app.Fragment;

/**
 * Created by wangzhuo-neu on 2014/12/23.
 */
public class MenuInfo {
    private int menu_name;
    private int img_src;

    private Fragment fragment;

    public  MenuInfo(){}

    public  MenuInfo(int menu_name,int img_src){
        this.menu_name = menu_name;
        this.img_src = img_src;
    }

    public  MenuInfo(int menu_name,int img_src,Fragment fragment){
        this.menu_name = menu_name;
        this.img_src = img_src;
        this.fragment = fragment;
    }
    public int getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(int menu_name) {
        this.menu_name = menu_name;
    }

    public int getImg_src() {
        return img_src;
    }

    public void setImg_src(int img_src) {
        this.img_src = img_src;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
