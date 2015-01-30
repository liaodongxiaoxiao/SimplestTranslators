package com.ldxx.simplesttranslator.view;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ldxx.simplesttranslator.R;
import com.ldxx.simplesttranslator.bean.WordInfo;
import com.ldxx.simplesttranslator.bean.Words;
import com.ldxx.simplesttranslator.utils.AppUtils;
import com.ldxx.utils.DateUtil;
import com.ldxx.utils.StringUtils;
import com.ldxx.utils.XUtils;
import com.lidroid.xutils.DbUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 展示字典查询结果控件
 *
 * @author wangzhuo-neu
 */
public class WordInfoTextView extends LinearLayout {
    private String TAG = this.getClass().getSimpleName();
    private Context context;

    private TextView q_word;
    private TextView q_phen;
    private TextView q_pham;
    private TextView q_phzh;
    private TextView q_mean;

    private DbUtils db;

    public WordInfoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WordInfoTextView(Context context) {
        super(context);
        init(context);

    }

    @SuppressLint("InflateParams")
    private void init(Context context) {
        this.context = context;
        View v = LayoutInflater.from(this.context).inflate(
                R.layout.com_ldxx_wordinfotextview, null);
        q_word = (TextView) v.findViewById(R.id.q_word);
        q_phen = (TextView) v.findViewById(R.id.q_phen);
        q_pham = (TextView) v.findViewById(R.id.q_pham);
        q_phzh = (TextView) v.findViewById(R.id.q_phzh);
        q_mean = (TextView) v.findViewById(R.id.q_means);
        db = AppUtils.application.getDb();
        this.addView(v);
    }

    public void setText(String str) {
        Log.e(TAG, str);
        try {

            JSONObject jObj = JSON.parseObject(str);

            //成功
            if ("0".equals(jObj.getString("errno"))) {

                Words words = new Words();
                //主键
                words.setPid(XUtils.getUUID());
                words.setQ_from(jObj.getString("from"));
                words.setQ_to(jObj.getString("to"));
                JSONObject obj = jObj.getJSONObject("data");
                words.setWord_name(obj.getString("word_name"));
                //获取翻译
                JSONObject means = obj.getJSONArray("symbols").getJSONObject(0);
                words.setPh_am(means.getString("ph_am"));
                words.setPh_en(means.getString("ph_en"));
                words.setPh_zh(means.getString("ph_zh"));
                List<WordInfo> list = JSON.parseArray(means.getString("parts"), WordInfo.class);

                db.save(words);

                for (WordInfo wi : list) {
                    wi.setPid(XUtils.getUUID());
                    wi.setWid(words.getPid());
                    wi.setMeans(StringUtils.removeDoubleQuotation(wi.getMeans()));
                }

                db.saveAll(list);
                //调用set方法
                setText(words, list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setText(Words words, List<WordInfo> wis) {
        q_word.setText(words.getWord_name());
        StringBuffer sb = new StringBuffer();
        for (WordInfo wf : wis) {
            sb.append("<b>").append(wf.getPart()).append("</b> ").append(wf.getMeans())
                    .append("<br/>");
        }
        q_mean.setText(Html.fromHtml(sb.toString()));
        //从中文到英文
        if ("zh".equals(words.getQ_from())) {
            if (!StringUtils.isEmpty(words.getPh_zh())) {
                q_phzh.setText("[拼音][" + words.getPh_zh() + "]");
            }
            q_pham.setVisibility(View.GONE);
            q_phen.setVisibility(View.GONE);
        } else {
            if (!StringUtils.isEmpty(words.getPh_am())) {
                q_pham.setText("[美][" + words.getPh_am() + "]");
            }
            if (!StringUtils.isEmpty(words.getPh_en())) {
                q_phen.setText("[英][" + words.getPh_en() + "]");
            }
        }
    }

}
