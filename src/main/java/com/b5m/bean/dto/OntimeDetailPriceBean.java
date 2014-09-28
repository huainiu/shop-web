package com.b5m.bean.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class OntimeDetailPriceBean {
	private int timeout = 3000;
	private String url;
	private String docid;
	private JSONArray keys = new JSONArray();

	public OntimeDetailPriceBean() {
		keys.add("OriginalPicture");
		keys.add("Price");
		keys.add("Title");
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public JSONArray getKeys() {
		return keys;
	}

	public void setKeys(JSONArray keys) {
		this.keys = keys;
	}

	public String toJsonString() {
		return JSON.toJSONString(this);
	}
}
