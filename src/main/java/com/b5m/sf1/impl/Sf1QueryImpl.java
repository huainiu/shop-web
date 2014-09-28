package com.b5m.sf1.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.utils.StringTools;
import com.b5m.sf1.Sf1Query;
import com.b5m.sf1.dto.req.SF1SearchBean;
import com.b5m.sf1.dto.res.SearchDTO;
import com.b5m.sf1.helper.Sf1DataHelper;
import com.b5m.sf1.helper.Sf1Helper;
import com.b5m.sf1.log.Sf1Log;

/**
 * @author echo
 */
@Service("sf1Query")
public class Sf1QueryImpl implements Sf1Query {
	@Autowired
	@Qualifier("sf1Config")
	private Properties sf1Config;

	@Override
	public JSONObject forecastPrice(JSONArray forecast) {
		String jsonString = Sf1Helper.buildForecastJson(forecast);
		String sf1Path = getSf1Path("forecast") + "/documents/forecast";
		JSONObject jsonObject = Sf1Helper.doSearch(sf1Path, jsonString);
		return jsonObject;
	}

	@Override
	/* @Cache */
	public int searchCount(SF1SearchBean searchBean) {
		String jsonString = Sf1Helper.buildSearchCountJson(searchBean);
		String sf1Path = getSf1Path() + "/documents/search";
		JSONObject jsonObject = Sf1Helper.doSearch(sf1Path, jsonString);
		return getResourcesCount(jsonObject);
	}

	@Override
	/* @Cache */
	public SearchDTO doSearch(SF1SearchBean searchBean) {
		SearchDTO searchDTO = new SearchDTO();
		String jsonString = Sf1Helper.buildSearchJson(searchBean);
		String sf1Path = getSf1Path(searchBean.getCollection()) + "/documents/search";
		JSONObject jsonObject = Sf1Helper.doSearch(sf1Path, jsonString);
		long totalCount = this.getResourcesCount(jsonObject);
		searchDTO.setTotalCount(totalCount);
		Sf1DataHelper.wrap(searchDTO, searchBean, jsonObject);
		Sf1DataHelper.setSpeedTime(searchDTO, jsonObject);
		Sf1DataHelper.setCategoryAndGroupInfo(searchDTO, jsonObject);
		Sf1DataHelper.setResources(searchDTO, searchBean, jsonObject);
		Sf1DataHelper.setRemovedKeywords(searchDTO, searchBean, jsonObject);
		searchDTO.setAnalyzerResult(jsonObject.getString("analyzer_result"));
		return searchDTO;
	}

	@Override
	/* @Cache */
	public SearchDTO doGet(SF1SearchBean searchBean) {
		// 返回的DTO对象
		SearchDTO searchDTO = new SearchDTO();
		// 创建请求用JSON
		String jsonString = Sf1Helper.buildGetJson(searchBean);
		String sf1Path = getSf1Path() + "/documents/get";
		// 请求获得返回结果
		JSONObject resultJsonObj = Sf1Helper.doSearch(sf1Path, jsonString);
		Sf1DataHelper.setResources(searchDTO, searchBean, resultJsonObj);
		return searchDTO;
	}

	@Override
	/* @Cache */
	public JSONObject doGetRtnJson(SF1SearchBean searchBean) {
		// 创建请求用JSON
		String jsonString = Sf1Helper.buildGetJson(searchBean);
		String sf1Path = null;
		if (jsonString.indexOf(Sf1Helper.CONTEXT_COLLECTION) > 0) {
			sf1Path = getSf1Path() + "/documents/get";
		} else {
			sf1Path = getSf1PathOnLine() + "/documents/get";
		}

		// 请求获得返回结果
		JSONObject resultJsonObj = Sf1Helper.doSearch(sf1Path, jsonString);
		return resultJsonObj;
	}

	@Override
	/* @Cache */
	public List<String> getRefined_query(String collection, String keywords) {
		String jsonString = Sf1Helper.buildRefinedJson(collection, keywords);
		String sf1Path = getSf1Path() + "/query_correction";
		JSONObject resultJsonObj = Sf1Helper.doSearch(sf1Path, jsonString);
		String keywordStr = resultJsonObj.getString("refined_query");
		return getRefinedKeyword(keywordStr);
	}

	@Override
	/* @Cache */
	public List<String[]> getSummarization(String collection, String docId) {
		// 创建请求用JSON
		JSONObject jsonDocumentSearch = Sf1Helper.buildSummarizationJson(collection, docId);
		String sf1Path = getSf1Path() + "/documents/get_summarization";
		// 请求获得返回结果
		JSONObject resultJsonObj = Sf1Helper.doSearch(sf1Path, jsonDocumentSearch.toString());
		List<String[]> results = Sf1DataHelper.getSummarizationFromResult(resultJsonObj);
		return results;
	}

	/**
	 * 从结果字符串中获得纠正关键字列表
	 * 
	 * @param result
	 *            纠正关键字列表
	 * @return
	 */
	private List<String> getRefinedKeyword(String result) {
		List<String> refinedkeywordList = new ArrayList<String>();
		if (StringTools.isEmpty(result))
			return refinedkeywordList;
		result = result.replace("|", ",");
		if (StringUtils.isNotBlank(result)) {// 设置纠错后的关键字列表
			String[] arrObj = result.split(",");
			for (int i = 0; i < arrObj.length; i++) {
				refinedkeywordList.add(arrObj[i]);
			}
		}
		return refinedkeywordList;
	}

	protected String getSf1Path(String... collection) {
		String ipName = "server_ip";
		if (collection != null && collection.length > 0 && !collection[0].equals(Sf1Helper.CONTEXT_COLLECTION)) {
			ipName += "_" + collection[0];
		}
		String ip = sf1Config.getProperty(ipName);
		String port = sf1Config.getProperty("port_num");
		String server = sf1Config.getProperty("server_path");
		return new StringBuilder().append("http://").append(ip).append(":").append(port).append("/").append(server).toString();
	}

	protected String getSf1PathOnLine(String... collection) {
		String ipName = "server_ip_onLine";
		if (collection != null && collection.length > 0 && !collection[0].equals(Sf1Helper.CONTEXT_COLLECTION)) {
			ipName += "_" + collection[0];
		}
		String ip = sf1Config.getProperty(ipName);
		String port = sf1Config.getProperty("port_num");
		String server = sf1Config.getProperty("server_path");
		return new StringBuilder().append("http://").append(ip).append(":").append(port).append("/").append(server).toString();
	}

	protected int getResourcesCount(JSONObject resultJsonObj) {
		Integer count = resultJsonObj.getInteger("total_count");
		if (count == null)
			count = 0;
		return count;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "\u5934";
		System.out.println(new String(s.getBytes(""), "UTF-8"));
	}
}
