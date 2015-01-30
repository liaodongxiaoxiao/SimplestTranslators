package com.ldxx.simplesttranslator.activity;

import java.io.File;
import java.io.FileNotFoundException;


import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.bmob.btp.file.BTPFileResponse;
import com.ldxx.simplesttranslator.R;
import com.ldxx.simplesttranslator.app.BaseActivity;
import com.ldxx.simplesttranslator.service.UploadPhotoService;
import com.ldxx.simplesttranslator.utils.AppUtils;
import com.ldxx.utils.FileUtils;
import com.ldxx.view.ImageCut;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class ChangeImgActivity extends BaseActivity {
    private ImageCut ic;

    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_pic);
        initToolBar(R.string.title_activity_change_photo);
        ic = (ImageCut) findViewById(R.id.cut_pic_view);
    }

    /**
     * 打开图库返回照片
     *
     * @param view
     */
    public void toChangeImg(View view) {
        Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
        startActivityForResult(intent, 1);
    }

    public void toCamera(View view) {
        // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // startActivityForResult(intent, 2);
        // 保存的路径
        File camera_path = FileUtils.getDiskCacheDir(this, "umg");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (!camera_path.exists()) {
            camera_path.mkdirs();
        }
        // camera_photo_name = System.currentTimeMillis() + ".png";
        file = new File(camera_path, "tmg.png");
        Uri mOutPutFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
        startActivityForResult(intent, 2);
    }

    public void toCut(View view) {
        Bitmap bitmap = ic.onClip();

        File cacheDir = FileUtils.getDiskCacheDir(this, "umg");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        FileUtils.saveBitmap(bitmap, FileUtils.getPhotoTmpPath(this));

        AppUtils.setDefaultSharedPreferencesBoolean("NEED_UPLOAD_IMG",true);
        startService(new Intent(this, UploadPhotoService.class));

        bitmap.recycle();
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr
                        .openInputStream(uri));
                bitmap = FileUtils.compressImageFromFile(this,uri);
                // ImageView imageView = (ImageView) findViewById(R.id.iv01);
				/* 将Bitmap设定到ImageView */
                ic.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        } else if (resultCode == RESULT_OK && requestCode == 2) {
            // Bundle bundle = data.getExtras();
            // Bitmap bitmap = (Bitmap) bundle.get("data");//
            // 获取相机返回的数据，并转换为Bitmap图片格式
            Bitmap bitmap = FileUtils.compressImageFromFile(FileUtils
                    .getDiskCacheFilePath(this, "umg", "tmg.png"));
            if (bitmap != null) {
                ic.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
