package com.b5m.bean.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 时时价格bean
 * @author echo
 * @time 2014年5月8日
 * @mail wuming@b5m.com
 */
public class OntimePriceBean {
	private int flag = 0;
	
	private int timeout = 4000;
	
	private JSONArray list = new JSONArray();

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	public JSONArray getList(){
		return list;
	}
	

	public void addDocId(String docid, String url){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("docid", docid);
		jsonObject.put("url", url);
		list.add(jsonObject);
	}
	
	public String toJsonString(){
		return JSON.toJSONString(this);
	}
	
}
