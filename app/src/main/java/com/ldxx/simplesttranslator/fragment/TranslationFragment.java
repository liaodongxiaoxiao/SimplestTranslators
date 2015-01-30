package com.ldxx.simplesttranslator.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldxx.simplesttranslator.R;

public final class TranslationFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";
    private String mContent = "???";
    // http://openapi.baidu.com/public/2.0/bmt/translate
    private String TAG = this.getClass().getSimpleName();

    public static TranslationFragment newInstance() {
        TranslationFragment fragment = new TranslationFragment();
        return fragment;
    }

    /**
     * 初始化UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(
                R.layout.trans_layout, null);
        return v;
    }

    /**
     * 保存当前状态
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
