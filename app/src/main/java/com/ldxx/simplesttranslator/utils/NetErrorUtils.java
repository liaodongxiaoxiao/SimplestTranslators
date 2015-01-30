package com.ldxx.simplesttranslator.utils;

import com.android.volley.VolleyError;

/**
 * Created by wangzhuo-neu on 2015/1/27.
 */
public class NetErrorUtils {

    public static String getErrorInfo(VolleyError error){
        String errorClassName = error.getClass().getName();
        if("com.android.volley.AuthFailureError".equals(errorClassName)){
            return "请求认证失败";
        }else if("com.android.volley.NoConnectionError".equals(errorClassName)){
            return "无法建立网络连接";
        }else if("com.android.volley.ServerError".equals(errorClassName)){
            return "服务器响应错误";
        }else if("com.android.volley.TimeoutError".equals(errorClassName)){
            return "请求超时";
        }else{
            return  "请求失败，请稍后重试";
        }

    }
}
