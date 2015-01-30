package com.ldxx.simplesttranslator.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.bmob.btp.file.BTPFileResponse;
import com.ldxx.simplesttranslator.bean.UserInfo;
import com.ldxx.simplesttranslator.utils.AppUtils;
import com.ldxx.utils.FileUtils;
import com.ldxx.utils.NetUtils;

/**
 *
 */
public class UploadPhotoService extends IntentService {


    public UploadPhotoService() {
        super("UploadPhotoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //网络可用
        if (NetUtils.isNetworkAvailable(getApplicationContext()) && AppUtils.getDefaultSharedPreferencesBoolean("NEED_UPLOAD_IMG")) {


            BTPFileResponse response = BmobProFile.getInstance(this).upload(FileUtils.getPhotoTmpPath(this), new UploadListener() {

                @Override
                public void onSuccess(String fileName, String url) {
                    AppUtils.setDefaultSharedPreferencesStr("PHOTO_NAME", fileName);
                    AppUtils.setDefaultSharedPreferencesStr("PHOTO_URL", url);
                    //dialog.dismiss();
                    Log.e("UploadPhotoService", "文件已上传成功：" + fileName + "url: " + url);
                    AppUtils.setDefaultSharedPreferencesBoolean("NEED_UPLOAD_IMG",false);

                    UserInfo userInfo = (UserInfo) UserInfo.getCurrentUser(getApplicationContext());
                    userInfo.setPhoto_url(url);
                    userInfo.update(getApplicationContext());

                }

                @Override
                public void onProgress(int ratio) {
                    //BmobLog.i("MainActivity -onProgress :"+ratio);
                }

                @Override
                public void onError(int statuscode, String errormsg) {
                    Log.e("UploadPhotoService", "上传出错：" + errormsg);
                }
            });
        }
    }
}
