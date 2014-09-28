package com.b5m.client.php;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.b5m.client.AbstractClient;
import com.b5m.client.HttpClientFactory;

public class PHPClient extends AbstractClient {

	public PHPClient(String url, HttpClientFactory httpClientFactory) {
		super(url, httpClientFactory);
	}

	@Value("#{sysConfig['php.client.other.info']}")
	String otherUrl;

	public JSONObject getHotInfo(String id) {
		String request = "a=get_yiyuanduihuan_product&id=" + id;
		GetMethod method = null;
		try {
			HttpClient httpClient = httpClientFactory.getHttpClient();
			httpClient.getParams().setSoTimeout(3000);
			method = createGetMethod(url, request);
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.warn("getHotInfo access failure, status code :" + statusCode);
				return null;
			}
			String resultMsg = method.getResponseBodyAsString().trim();
			logger.info("getHotInfo for result[" + resultMsg + "], for request[" + request + "]");
			return JSON.parseObject(resultMsg);
		} catch (Exception e) {
			return null;
		} finally {
			if (method != null)
				method.releaseConnection();
		}
	}

	public JSONObject getComments(String id, Integer num) {
		String request = "a=get_zdm_quick_comment_by_docid&docid=" + id + "&num=" + num;
		GetMethod method = null;
		try {
			HttpClient httpClient = httpClientFactory.getHttpClient();
			httpClient.getParams().setSoTimeout(3000);
			method = createGetMethod(otherUrl, request);
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.warn("getHotInfo access failure, status code :" + statusCode);
				return null;
			}
			String resultMsg = method.getResponseBodyAsString().trim();
			logger.info("getHotInfo for result[" + resultMsg + "], for request[" + request + "]");
			return JSON.parseObject(resultMsg);
		} catch (Exception e) {
			return null;
		} finally {
			if (method != null)
				method.releaseConnection();
		}
	}

}
