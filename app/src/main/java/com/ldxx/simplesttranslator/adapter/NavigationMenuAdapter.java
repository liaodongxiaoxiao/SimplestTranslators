package com.ldxx.simplesttranslator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.ldxx.simplesttranslator.R;
import com.ldxx.simplesttranslator.bean.MenuInfo;

import java.util.List;

/**
 * Created by wangzhuo-neu on 2014/12/23.
 */
public class NavigationMenuAdapter extends BaseAdapter {
    private Context context;
    private List<MenuInfo> data;

    public NavigationMenuAdapter(Context context, List<MenuInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        MenuHolder mh =null;
        if (v == null) {
            v = LayoutInflater.from(this.context).inflate(R.layout.navigation_menuitem, null);
            mh =  new MenuHolder();
            mh.iv = (ImageView) v.findViewById(R.id.menu_icon);
            mh.tv = (TextView) v.findViewById(R.id.menu_name);
            v.setTag(mh);
        }else{
            mh = (MenuHolder) v.getTag();
        }
        MenuInfo mi = data.get(position);
        mh.tv.setText(mi.getMenu_name());
        mh.iv.setImageResource(mi.getImg_src());
        return v;
    }

    class MenuHolder{
        private TextView tv;
        private ImageView iv;
    }
}
