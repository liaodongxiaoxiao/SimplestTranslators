package com.ldxx.simplesttranslator.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ldxx.simplesttranslator.R;
import com.ldxx.simplesttranslator.bean.WordInfo;
import com.ldxx.simplesttranslator.bean.Words;
import com.ldxx.simplesttranslator.utils.AppUtils;
import com.ldxx.utils.StringUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class WordQueryAdapter extends CursorAdapter {
	public final String TAG = this.getClass().getSimpleName();
	public static final String action = "com.ldxx.simplesttranslator.queryfromlocal.action";  
	private LayoutInflater layoutInflater;
	private Activity activity;

	@Override
	public CharSequence convertToString(Cursor cursor) {
		return cursor == null ? "" : cursor.getString(cursor
				.getColumnIndex("word_name"));
	}

	private void setView(View view, Cursor cursor) {
		TextView tvWordItem = (TextView) view;
		String str = cursor.getString(cursor.getColumnIndex("word_name"));
		tvWordItem.setText(str);
		DbUtils db = AppUtils.application.getDb();
		try {
			Words words = db.findFirst(Selector.from(Words.class).where(
					"word_name", "=", str));
			List<WordInfo> wif = new ArrayList<WordInfo>();
			if (words != null && StringUtils.isEmpty(words.getPid())) {
				wif = db.findAll(Selector.from(WordInfo.class).where("wid",
						"=", words.getPid()));
			}
			//ShowResult sr = new MainActivity.ShowResult(words,wif);
			//activity.runOnUiThread(new ShowResult(words,wif));
            Intent intent = new Intent(action);  
            //intent.putExtra("data", "yes i am data");
            Bundle bundle = new Bundle();
            bundle.putSerializable("words", words);
            bundle.putSerializable("wordInfo", (Serializable)wif);
            intent.putExtras(bundle);
            ///intent.putExtra("words", words);
            activity.sendBroadcast(intent);  
		} catch (DbException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		Log.e(TAG, "----------- bindView");
		setView(view, cursor);
	}

	@SuppressLint("InflateParams")
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = layoutInflater.inflate(R.layout.com_ldxx_st_wordlistitem,
				null);
		Log.e(TAG, "----------- newView");
		setView(view, cursor);
		return view;
	}

	public WordQueryAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		activity = (Activity) context;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

}
