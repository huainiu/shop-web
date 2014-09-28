package com.b5m.client.ontimeprice.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.client.AbstractClient;
import com.b5m.client.HttpClientFactory;
import com.b5m.client.ontimeprice.OntimeClient;
import com.b5m.service.ontimeprice.OntimeLogUtils;

/**
 * @author echo
 * @time 2014年5月8日
 * @mail wuming@b5m.com
 */
public class OntimeClientImpl extends AbstractClient implements OntimeClient {
	private static final Log log = LogFactory.getLog(OntimeClientImpl.class);
	@Resource(name = "properties")
	private Properties properties;
	private String[] fetchPriceSits;
	private String[] fetchSKUSits;

	String DETAIL_URL;

	public OntimeClientImpl(String url, String detailUrl, HttpClientFactory httpClientFactory) {
		super(url, httpClientFactory);
		this.DETAIL_URL = detailUrl;
	}

	@Override
	public JSONArray queryPrices(String request) throws HttpException, IOException {
		if (filterFetchPrice(request))
			return new JSONArray();
		PostMethod method = null;
		long time = System.currentTimeMillis();
		try {
			HttpClient httpClient = httpClientFactory.getHttpClient();
			httpClient.getParams().setSoTimeout(3000);
			method = createPostMethod(url, request);
			int statusCode = httpClient.executeMethod(method);
			OntimeLogUtils.info("queryPrices use " + (System.currentTimeMillis() - time) + " ms");
			if (statusCode != HttpStatus.SC_OK) {
				return new JSONArray();
			}
			String resultMsg = method.getResponseBodyAsString().trim();
			OntimeLogUtils.info("on time query price for result[" + resultMsg + "], for request[" + request + "]");
			return JSON.parseArray(resultMsg);
		} finally {
			if (method != null)
				method.releaseConnection();
		}
	}

	private boolean filterFetchPrice(String request) {
		JSONObject jsonObject = JSONObject.parseObject(request);
		JSONArray jsonArray = jsonObject.getJSONArray("list");
		if (jsonArray.size() < 1)
			return true;
		int length = jsonArray.size();
		String[] fetchPriceSits = canFetchPriceSit();
		for (int index = 0; index < length; index++) {
			JSONObject rel = jsonArray.getJSONObject(index);
			String url = rel.getString("url");
			if (canFetch(fetchPriceSits, url)) {
				return false;
			}
		}
		return true;
	}

	private boolean canFetch(String[] fetchSits, String url) {
		for (int i = 0; i < fetchSits.length; i++) {
			if (url.indexOf(fetchSits[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

	private String[] canFetchPriceSit() {
		if (fetchPriceSits == null) {
			String fetchPriceSit = properties.getProperty("ontime.fetch.price.sources");
			fetchPriceSits = StringUtils.split(fetchPriceSit, ",");
		}
		return fetchPriceSits;
	}

	private String[] canFetchSKUSit() {
		if (fetchSKUSits == null) {
			String fetchSKUSit = properties.getProperty("ontime.fetch.sku.sources");
			fetchSKUSits = StringUtils.split(fetchSKUSit, ",");
		}
		return fetchSKUSits;
	}

	@Override
	public JSONObject querySku(String request) throws HttpException, IOException {
		if (filterFetchSKU(request))
			return new JSONObject();
		PostMethod method = null;
		long time = System.currentTimeMillis();
		try {
			HttpClient httpClient = httpClientFactory.getHttpClient();
			httpClient.getParams().setSoTimeout(5000);
			method = createPostMethod(DETAIL_URL, request);
			int statusCode = httpClient.executeMethod(method);
			OntimeLogUtils.info("query sku use " + (System.currentTimeMillis() - time) + " ms");
			if (statusCode != HttpStatus.SC_OK) {
				return new JSONObject();
			}
			String resultMsg = method.getResponseBodyAsString().trim();
			OntimeLogUtils.info("ontime query sku for result[" + resultMsg + "], for request[" + request + "]");
			return JSON.parseObject(resultMsg);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		String URL = "http://172.16.1.216:8080/icache/getSku.json";
		String content = "{\"keys\":[\"DOCID\"],\"timeout\":5000,\"url\":\"http://detail.tmall.com/item.htm?id=36738657545\"}";
		PostMethod method = new PostMethod(URL);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		method.setRequestEntity(new StringRequestEntity(content, "application/json", "UTF-8"));
		
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setSoTimeout(5000);
		int statusCode = httpClient.executeMethod(method);
		if (statusCode != HttpStatus.SC_OK) {
			System.out.println("状态错误, code :" + statusCode);
			return ;
		}
		String resultMsg = method.getResponseBodyAsString().trim();
		OntimeLogUtils.info("ontime query sku for result[" + resultMsg + "]");
		System.out.println(JSON.parseObject(resultMsg));
	}

	private boolean filterFetchSKU(String request) {
		JSONObject jsonObject = JSONObject.parseObject(request);
		String url = jsonObject.getString("url");
		String[] fetchSKUSits = canFetchSKUSit();
		if (canFetch(fetchSKUSits, url)) {
			return false;
		}
		return true;
	}

	@Override
	public JSONObject queryDetailPrice(String request) throws HttpException, IOException {
		PostMethod method = null;
		long time = System.currentTimeMillis();
		try {
			HttpClient httpClient = httpClientFactory.getHttpClient();
			method = createPostMethod(DETAIL_URL, request);
			int statusCode = httpClient.executeMethod(method);
			OntimeLogUtils.info("queryDetailPrice use " + (System.currentTimeMillis() - time) + " ms");
			if (statusCode != HttpStatus.SC_OK) {
				return new JSONObject();
			}
			String resultMsg = method.getResponseBodyAsString().trim();
			OntimeLogUtils.info("on detail price for result[" + resultMsg + "], for request[" + request + "]");
			return JSON.parseObject(resultMsg);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
	}

}
