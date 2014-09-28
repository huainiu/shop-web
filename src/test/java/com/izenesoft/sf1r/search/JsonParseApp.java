package com.izenesoft.sf1r.search;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.service.hbase.bean.CommentType;

public class JsonParseApp {

	public static void main(String[] args) throws IOException {
		/*String json = IOUtils.toString(JsonParseApp.class.getResourceAsStream("json.txt"));
		JSONObject jsonObject = JSONObject.parseObject(json);
		JSONArray jsonArray = jsonObject.getJSONArray("resources");
		System.out.println(jsonArray);*/
		
		System.out.println(CommentType.valueOf("BAD"));
		System.out.println(CommentType.valueOf("bad"));
	}
	
}
