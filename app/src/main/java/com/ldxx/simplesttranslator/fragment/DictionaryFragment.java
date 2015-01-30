package com.ldxx.simplesttranslator.fragment;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ldxx.simplesttranslator.R;
import com.ldxx.simplesttranslator.adapter.WordQueryAdapter;
import com.ldxx.simplesttranslator.bean.WordInfo;
import com.ldxx.simplesttranslator.bean.Words;
import com.ldxx.simplesttranslator.utils.AppUtils;
import com.ldxx.simplesttranslator.utils.NetErrorUtils;
import com.ldxx.simplesttranslator.view.WordInfoTextView;
import com.ldxx.utils.KeyboardUtil;
import com.ldxx.utils.StringUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import org.json.JSONObject;

/**
 * 词典Fragment页面
 */
public final class DictionaryFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";

    private String TAG = this.getClass().getSimpleName();

    private WordInfoTextView result;

    private AutoCompleteTextView words;

    private ToggleButton queryType;

    private Button queryBtn;

    private String from = "en";
    private String to = "zh";
    private ProgressDialog dialog;

    private DbUtils db;

    public static DictionaryFragment newInstance() {
        DictionaryFragment fragment = new DictionaryFragment();
        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null)
                && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(
                R.layout.dic_layout, null);
        db = AppUtils.application.getDb();
        result = (WordInfoTextView) v.findViewById(R.id.tv_result);
        queryBtn = (Button) v.findViewById(R.id.q_querybtn);

        queryBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 隐藏键盘
                KeyboardUtil.hideSoftInput(getActivity());

                final Message msg = handler.obtainMessage();
                final Bundle b = new Bundle();
                String word = words.getText().toString();
                if (!StringUtils.isEmpty(word)) {
                    dialog.show();
                    //转换一下单词
                    try {
                      word =  URLEncoder.encode(word,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    // 要访问的URL对象
                    RequestQueue mQueue = Volley.newRequestQueue(getActivity());
                    mQueue.add(new JsonObjectRequest(Request.Method.GET, getUrl(word), null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.e(TAG, "response : " + response.toString());
                                    b.putString("result", response.toString());
                                    msg.what = 1;
                                    msg.setData(b);
                                    handler.sendMessage(msg);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.e(TAG, "error:" + NetErrorUtils.getErrorInfo(volleyError));
                            //volleyError.getCause().getClass().getName().equals("")
                            b.putString("result", NetErrorUtils.getErrorInfo(volleyError));
                            msg.what = -1;
                            msg.setData(b);
                            handler.sendMessage(msg);
                        }
                    }));
                    mQueue.start();

                } else {
                    msg.what = 0;
                    b.putString("result", "单词不能为空");
                    msg.setData(b);
                    handler.sendMessage(msg);
                }


            }
        });
        words = (AutoCompleteTextView) v.findViewById(R.id.et_words);
        words.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 获取自动填充提示框中选中的单词
                String str = ((TextView) view).getText().toString();
                // 根据选中的单词，从本地查询出结果并显示
                try {
                    Words words = db.findFirst(Selector.from(Words.class)
                            .where("word_name", "=", str));
                    List<WordInfo> wif = new ArrayList<WordInfo>();
                    if (words != null && !StringUtils.isEmpty(words.getPid())) {
                        wif = db.findAll(Selector.from(WordInfo.class).where(
                                "wid", "=", words.getPid()));
                    }

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("words", words);
                    bundle.putSerializable("wordInfo", (Serializable) wif);
                    Message sg = new Message();
                    sg.setData(bundle);
                    sg.what = 2;
                    handler.sendMessage(sg);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });

        words.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String contentStr = s.toString();

                if (StringUtils.isEmpty(contentStr))// 判断contentStr是否为空,判断字符串是否为空典型写法
                {
                    queryBtn.setEnabled(false);// 为空则不是能按钮

                } else {
                    queryBtn.setEnabled(true);
                    Cursor cursor;
                    try {
                        StringBuffer sql = new StringBuffer(
                                " select pid as _id,word_name from Words where word_name like '");
                        sql.append(contentStr.trim()).append("%' ");
                        cursor = db.execQuery(sql.toString());

                        WordQueryAdapter dictionaryAdapter = new WordQueryAdapter(
                                getActivity(), cursor, true);
                        words.setAdapter(dictionaryAdapter);

                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("查询中，请稍等...");
        dialog.setCancelable(false);
        queryType = (ToggleButton) v.findViewById(R.id.q_querytype);
        queryType.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    from = "en";
                    to = "zh";
                } else {
                    from = "zh";
                    to = "en";
                }
            }
        });

        return v;
    }

    private String getUrl(String word) {
        StringBuffer url = new StringBuffer(
                "http://openapi.baidu.com/public/2.0/translate/dict/simple?from=");
        url.append(from).append("&to=").append(to)
                .append("&client_id=UE6gHRY2LL3pjXKe66YaIQqQ&q=")
                .append(word);
        Log.e("getUrl",url.toString());
        return url.toString();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }

    // handler处理查询结果
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            dialog.cancel();
            String reStr = "";
            Bundle b = msg.getData();
            if (b != null && b.containsKey("result")) {
                reStr = b.getString("result");
            }
            switch (msg.what) {
                case 1:
                    result.setText(reStr);
                    break;
                case 2:
                    if (b != null) {
                        Words words = (Words) b.get("words");
                        List<WordInfo> wif = (List<WordInfo>) b.get("wordInfo");
                        result.setText(words, wif);
                    }
                    break;
                default:
                    Toast.makeText(getActivity(), reStr, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


}
