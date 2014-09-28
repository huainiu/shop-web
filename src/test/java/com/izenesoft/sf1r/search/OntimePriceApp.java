package com.izenesoft.sf1r.search;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.Test;

import com.b5m.base.common.httpclient.HttpClientFactory;
import com.b5m.bean.dto.OntimePriceBean;

public class OntimePriceApp {
	
	@Test
	public void testGetPrice() throws IOException {
		HttpClient httpClient = HttpClientFactory.getHttpClient();
		OntimePriceBean ontimePriceBean = new OntimePriceBean();
		ontimePriceBean.addDocId("3b1b6cc9aacf45fe494896614c54a53b", "http://www.amazon.cn/mn/detailApp/?asin=B0090X0XW8");
		ontimePriceBean.addDocId("3bf1980b0a01c36cc2f316b0435b782d", "http://product.dangdang.com/60545904.html");
		ontimePriceBean.addDocId("11619be75e6b3807748b1c521202cc66", "");
		PostMethod method = createPostMethod("http://getprice.b5m.com/CrawlerPrice/get", ontimePriceBean.toJsonString());
		int statusCode = httpClient.executeMethod(method);
		String resultMsg = method.getResponseBodyAsString().trim();
		System.out.println(resultMsg);
	}
	
	private static PostMethod createPostMethod(String URL, String content) throws UnsupportedEncodingException {
		PostMethod method = new PostMethod(URL);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		method.setRequestEntity(new StringRequestEntity(content, "application/json", "UTF-8"));
		method.setRequestHeader("Connection", "Keep-Alive");
		return method;
	}
	
}
