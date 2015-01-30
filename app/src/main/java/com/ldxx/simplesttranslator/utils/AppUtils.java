package com.ldxx.simplesttranslator.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ldxx.simplesttranslator.app.STApplication;

public class AppUtils {
	public static STApplication application;

    public static  SharedPreferences getDefaulSharedPreferences(){
        String name =application.getApplicationInfo().packageName.toUpperCase();
       return application.getSharedPreferences(name, Activity.MODE_PRIVATE);
    }

    public static void setDefaultSharedPreferencesStr(String key,String value){
        SharedPreferences.Editor editor = getDefaulSharedPreferences().edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getDefaultSharedPreferencesStr(String key){
        return getDefaulSharedPreferences().getString(key, "");
    }

    public static  void setDefaultSharedPreferencesBoolean(String key,boolean value){
        SharedPreferences.Editor editor = getDefaulSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getDefaultSharedPreferencesBoolean(String key){
        return getDefaulSharedPreferences().getBoolean(key, false);
    }
}
