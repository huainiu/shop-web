package com.izenesoft.sf1r.search;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.b5m.base.common.httpclient.HttpClientFactory;

public class AutoFillSearchApp {

    @Test
    public void testAutoFill() throws IOException{
    	HttpClient httpClient = HttpClientFactory.getHttpClient();
		String url = "http://10.10.99.188:8888/sf1r/auto_fill";
		String json = IOUtils.toString(AutoFillSearchApp.class.getResourceAsStream("jsonautofill.json"));
		PostMethod method = createPostMethod(url, json);
		int statusCode = httpClient.executeMethod(method);
		String resultMsg = method.getResponseBodyAsString().trim();
//		FileUtils.write(new java.io.File("/home/echo/test/jsonpsearchdata3.txt"), resultMsg, "UTF-8");
		System.out.println(statusCode);
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
