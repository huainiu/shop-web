package com.izenesoft.sf1r.search;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.httpclient.HttpClientFactory;
import com.b5m.sf1.dto.res.GroupTree;
import com.b5m.sf1.dto.res.SearchDTO;
import com.b5m.sf1.helper.Sf1DataHelper;

public class SF1App {

	private static final String DOCID = "4489ad1498a2d7b35eb80943b46434c5";

	@Test
	public void sf1Get() throws IOException {
		HttpClient httpClient = HttpClientFactory.getHttpClient();
		String url = "http://10.10.99.188:8888/sf1r/documents/get";
		// String url = "http://10.10.99.188:8888/sf1r/documents/search";
		String json = IOUtils.toString(this.getClass().getResourceAsStream("jsonpget.json"));
		PostMethod method = createPostMethod(url, json);
		int statusCode = httpClient.executeMethod(method);
		System.out.println(statusCode);
		String resultMsg = method.getResponseBodyAsString().trim();
		System.out.println(resultMsg);
		// FileUtils.write(new java.io.File("/home/echo/jsonpsearchdata.txt"),
		// resultMsg, "UTF-8");
	}
	
	@Test
	public void testDoPost() throws IOException {
		HttpClient httpClient = HttpClientFactory.getHttpClient();
		String url = "http://localhost:8080/B5MCMS/b5mcmsyagoods.do";
		// String url = "http://10.10.99.188:8888/sf1r/documents/search";
		String json = IOUtils.toString(this.getClass().getResourceAsStream("jsonpget.json"));
		PostMethod method = createPostMethod(url, json);
		int statusCode = httpClient.executeMethod(method);
		String resultMsg = method.getResponseBodyAsString().trim();
		System.out.println(resultMsg);
	}

	@Test
	public void sf1Search() throws IOException {
		HttpClient httpClient = HttpClientFactory.getHttpClient();
//		 String url = "http://10.10.99.188:8888/sf1r/documents/get";
//		String url = "http://10.10.96.188:8888/sf1r/documents/search";
//		String url = "http://10.10.99.188:8888/sf1r/documents/search";
//		String url = "http://10.10.96.188:8888/sf1r/documents/forecast";
		String url = "http://10.10.99.188:6333/search";
		String json = IOUtils.toString(this.getClass().getResourceAsStream("autoFill.json"));
		System.out.println(json);
		PostMethod method = createPostMethod(url, json);
		httpClient.setConnectionTimeout(50000);
		httpClient.getParams().setSoTimeout(50000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(50000);
		
		int statusCode = httpClient.executeMethod(method);
		System.out.println("statusCode : " + statusCode);
		String resultMsg = method.getResponseBodyAsString().trim();
//		System.out.println(resultMsg);
//		category(resultMsg);
		// System.out.println(resultMsg);2b15be1b8583ad61a9c4715e6abda405
//		FileUtils.write(new java.io.File("/home/echo/jsonpsearchdata.txt"), resultMsg, "UTF-8");
		System.out.println("查询完成.......");
		System.out.println(resultMsg);
	}
	
	@Test
	public void sf1SearchForurl() throws IOException {
		HttpClient httpClient = HttpClientFactory.getHttpClient();
		String url = "http://10.10.99.177:8888/sf1r/documents/search";
		String json = IOUtils.toString(this.getClass().getResourceAsStream("jsonpsearch.json"));
		PostMethod method = createPostMethod(url, json);
		int statusCode = httpClient.executeMethod(method);
		System.out.println("statusCode : " + statusCode);
		String resultMsg = method.getResponseBodyAsString().trim();
		JSONObject jsonObject = JSONObject.parseObject(resultMsg);
		List<String> list = new ArrayList<String>();
		JSONArray jsonArray = jsonObject.getJSONArray("resources");
		int length = jsonArray.size();
		for(int i = 0; i < length; i++){
			JSONObject shop = jsonArray.getJSONObject(i);
			JSONArray subDocs = shop.getJSONArray("SubDocs");
			if(subDocs == null){
				list.add(shop.getString("Url"));
				continue;
			}
			int subDocsLength = subDocs.size();
			if(subDocsLength > 0){
				for(int j = 0; j < subDocsLength; j++){
					JSONObject subShop = subDocs.getJSONObject(j);
					list.add(subShop.getString("Url"));
				}
			}else{
				list.add(shop.getString("Url"));
			}
		}
		for (String _url : list) {
			System.out.println(_url);
		}
	}

	private static PostMethod createPostMethod(String URL, String content) throws UnsupportedEncodingException {
		PostMethod method = new PostMethod(URL);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		method.setRequestEntity(new StringRequestEntity(content, "application/json", "UTF-8"));
		method.setRequestHeader("Connection", "Keep-Alive");
		return method;
	}
	
	public void category(String resultMsg){
		JSONObject jsonObject = JSONObject.parseObject(resultMsg);
		SearchDTO searchDTO = new SearchDTO();
		Sf1DataHelper.setCategoryAndGroupInfo(searchDTO, jsonObject);
		JSONObject category = new JSONObject();
		int i = 0;
		GroupTree categoryGroupTree = searchDTO.getCategoryTree();
		List<GroupTree> groupTrees = categoryGroupTree.getGroupTree();
		for(GroupTree groupTree : groupTrees){
			String firstGroupName = groupTree.getGroup().getGroupName();
			String num = full4Bit(i++);
			category.put(num, firstGroupName);
			category.put(firstGroupName, num);
			for(GroupTree second : groupTree.getGroupTree()){
				String secondGroupName = second.getGroup().getGroupName();
				num = full4Bit(i++);
				category.put(num, firstGroupName + ">" + secondGroupName);
				category.put(firstGroupName + ">" + secondGroupName, num);
				for(GroupTree third : second.getGroupTree()){
					String thirdGroupName = third.getGroup().getGroupName();
					
					num = full4Bit(i++);
					category.put(num, firstGroupName + ">" + secondGroupName + ">" + thirdGroupName);
					category.put(firstGroupName + ">" + secondGroupName + ">" + thirdGroupName, num);
				}
			}
		}
		System.out.println(category.toJSONString());
	}
	
	public String full4Bit(int i){
		String value = String.valueOf(i);
		switch (value.length()) {
			case 1:
				return "000" + value;
			case 2:
				return "00" + value;
			case 3:
				return "0" + value;
		    default:
		    	return value;
		}
	}
}
