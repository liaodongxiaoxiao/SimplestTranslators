package com.ldxx.simplesttranslator.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ldxx.simplesttranslator.R;
import com.ldxx.simplesttranslator.adapter.NavigationMenuAdapter;
import com.ldxx.simplesttranslator.app.BaseActivity;
import com.ldxx.simplesttranslator.app.STApplication;
import com.ldxx.simplesttranslator.bean.MenuInfo;
import com.ldxx.simplesttranslator.bean.UserInfo;
import com.ldxx.simplesttranslator.fragment.DictionaryFragment;
import com.ldxx.simplesttranslator.fragment.TranslationFragment;
import com.ldxx.simplesttranslator.view.CircleImageView;
import com.ldxx.utils.FileUtils;
import com.ldxx.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends BaseActivity {
    public static final String WORD_PATH = "http://openapi.baidu.com/public/2.0/translate/dict/simple";

    private final int REQUEST_LOGIN = 1;

    private final int REQUEST_SIGNIN = 2;

    private String TAG = this.getClass().getSimpleName();
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    //显示用户名的
    private TextView userName;

    private ListView menuList;

    private TextView login;
    private TextView sign;
    //头像
    private CircleImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        selectMenu(0);


        //初始化Bmob
        Bmob.initialize(this, STApplication.BMOB_APPKEY);
        setUserInfo();
    }

    private void initView() {
        //设置Actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.setTitle(R.string.app_name);// 标题的文字需在setSupportActionBar之前，不然会无效
        // toolbar.setSubtitle("副标题");
        setSupportActionBar(toolbar);
        /* 菜单的监听可以在toolbar里设置，也可以像ActionBar那样，通过下面的两个回调方法来处理 */
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);

        menuList = (ListView) findViewById(R.id.menu_list);
        NavigationMenuAdapter adapter = new NavigationMenuAdapter(this, getMenuData());
        menuList.setAdapter(adapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectMenu(position);
            }
        });

        userName = (TextView) findViewById(R.id.user_name);
        login = (TextView) findViewById(R.id.login_tv);
        sign = (TextView) findViewById(R.id.sign_tv);

        photo = (CircleImageView) findViewById(R.id.photo_img);

    }

    private void selectMenu(int position) {
        Fragment fragment = getMenuData().get(position).getFragment();
        menuList.setSelection(0);
        drawerLayout.closeDrawer(Gravity.LEFT);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private List<MenuInfo> getMenuData() {
        List<MenuInfo> list = new ArrayList<>();
        list.add(new MenuInfo(R.string.menu_home, R.drawable.menu_home, new DictionaryFragment()));
        list.add(new MenuInfo(R.string.menu_dic, R.drawable.menu_find, new TranslationFragment()));
        //list.add(new MenuInfo(R.string.menu_home,R.drawable.menu_home));
        //list.add(new MenuInfo(R.string.menu_home,R.drawable.menu_home));
        //list.add(new MenuInfo(R.string.menu_home,R.drawable.menu_home));
        return list;
    }

    /**
     * 跳转到登录页面
     *
     * @param view
     */
    public void toLogin(View view) {
        startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);
    }

    /**
     * 跳转到注册页面
     *
     * @param view
     */
    public void toSignIn(View view) {
        startActivityForResult(new Intent(this, SignInActivity.class), REQUEST_SIGNIN);
    }

    /**
     * 修改头像
     *
     * @param view
     */
    public void toChangePhoto(View view) {
        startActivityForResult(new Intent(this, ChangeImgActivity.class), REQUEST_SIGNIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        setUserInfo();
    }

    private void setUserInfo() {
        UserInfo user = BmobUser.getCurrentUser(this, UserInfo.class);
        //当前用户不为空并且用户通过邮箱验证，即为登录成功
        if (user != null && user.getEmailVerified()) {
            userName.setText(user.getUsername());
            userName.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
            sign.setVisibility(View.GONE);
            photo.setClickable(true);
            //头像路径存在，表明设置过自己的头像

            //若本地有缓存,设置头像
            if (FileUtils.fileExists(FileUtils.getPhotoTmpPath(this))) {
                Bitmap bitmap = FileUtils.getBitmap(FileUtils.getPhotoTmpPath(this));
                if (bitmap != null) {
                    photo.setImageBitmap(bitmap);
                }

            } else//若本地不存在缓存，需下载
            {

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawerLayout.closeDrawer(Gravity.LEFT);
    }
}
