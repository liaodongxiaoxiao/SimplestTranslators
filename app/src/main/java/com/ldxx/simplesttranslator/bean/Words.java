package com.ldxx.simplesttranslator.bean;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@SuppressWarnings("serial")
@Table(name = "Words")
public class Words implements Serializable {
	@Id
	private String pid;
	private String word_name;
	private String q_from;
	private String q_to;
	private String ph_am;
	private String ph_en;
	private String ph_zh;
	private String createtime;
    private boolean star;
    private String star_date;

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    public String isStar_date() {
        return star_date;
    }

    public void setStar_date(String star_date) {
        this.star_date = star_date;
    }

    public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getWord_name() {
		return word_name;
	}

	public void setWord_name(String word_name) {
		this.word_name = word_name;
	}

	public String getPh_am() {
		return ph_am;
	}

	public void setPh_am(String ph_am) {
		this.ph_am = ph_am;
	}

	public String getPh_en() {
		return ph_en;
	}

	public void setPh_en(String ph_en) {
		this.ph_en = ph_en;
	}

	public String getPh_zh() {
		return ph_zh;
	}

	public void setPh_zh(String ph_zh) {
		this.ph_zh = ph_zh;
	}

	public String getQ_from() {
		return q_from;
	}

	public void setQ_from(String q_from) {
		this.q_from = q_from;
	}

	public String getQ_to() {
		return q_to;
	}

	public void setQ_to(String q_to) {
		this.q_to = q_to;
	}

}
