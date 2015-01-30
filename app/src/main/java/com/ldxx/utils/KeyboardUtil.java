package com.ldxx.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtil {
	public static void hideSoftInput(Activity acitivity) {
		InputMethodManager imm = (InputMethodManager) acitivity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(acitivity.getWindow().getDecorView()
				.getApplicationWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static void showSoftInput(EditText et) {
		et.requestFocus();
		InputMethodManager imm = (InputMethodManager) et.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(et, InputMethodManager.RESULT_UNCHANGED_SHOWN);

	}

	public static void showSoftInputDelay(final EditText et) {
		et.postDelayed(new Runnable() {

			@Override
			public void run() {
				showSoftInput(et);
			}
		}, 300);
	}
}
