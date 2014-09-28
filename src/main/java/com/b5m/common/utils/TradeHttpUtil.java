package com.b5m.common.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class TradeHttpUtil {
	private static HttpClient httpClient;
	
	private static String TRADE_URL;
	
	private static Properties properties;
	
	static{
		
		properties = (Properties) ContextUtils.getBean("sysConfig");
		TRADE_URL = properties.getProperty("trade.url");
		MultiThreadedHttpConnectionManager connectionManager =

                new MultiThreadedHttpConnectionManager();
		
		httpClient = new HttpClient(connectionManager);
		
		//每主机最大连接数和总共最大连接数，通过hosfConfiguration设置host来区分每个主机  
		httpClient.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(8);
		httpClient.getHttpConnectionManager().getParams().setMaxTotalConnections(48);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
		httpClient.getHttpConnectionManager().getParams().setTcpNoDelay(true);
		httpClient.getHttpConnectionManager().getParams().setLinger(1000);
	    //失败的情况下会进行3次尝试,成功之后不会再尝试
		httpClient.getHttpConnectionManager().getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
	}
	
	public static JSONObject post2Msg(String url, Object parameterObj){
		JSONObject json = new JSONObject();
		json.put("ok", false);
		if(StringUtils.isBlank(url)) {
			json.put("msg", "请求地址为空");
			return json;
		}
		PostMethod postMethod = new PostMethod(TRADE_URL+url);
		if(parameterObj != null)
			postMethod.addParameters(getFieldsFiltered(parameterObj));
		
		String result = null;
		try {
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
			httpClient.executeMethod(postMethod);
			result = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			json.put("msg", "服务器链接失败");
			return json;
		}
		
		if(result == null) {
			json.put("msg", "请求返回内容异常");
			return json;
		}
		JSONObject res = JSON.parseObject(result);
		
		boolean isOk = "SUCCESS".equals(res.getString("message"));
		json.put("msg", res.getString("message"));
		json.put("ok", isOk);
		json.put("val", res);
		return json;
	}
	
	public static JSONObject postMsg(String url, Object parameterObj){
		JSONObject json = new JSONObject();
		json.put("ok", false);
		if(StringUtils.isBlank(url)) {
			json.put("msg", "请求地址为空");
			return json;
		}
		PostMethod postMethod = new PostMethod(url);
		if(parameterObj != null)
			postMethod.addParameters(getFieldsFiltered(parameterObj));
		
		String result = null;
		try {
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
			httpClient.executeMethod(postMethod);
			result = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			json.put("msg", "服务器链接失败");
			return json;
		}
		
		if(result == null) {
			json.put("msg", "请求返回内容异常");
			return json;
		}
		json = JSON.parseObject(result);
		return json;
	}
	
	private static NameValuePair[] getFieldsFiltered(Object classNameObj) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
        try {
            Class<? extends Object> clz = classNameObj.getClass();
            Method[] methods = clz.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName().startsWith("get")) {
                	Object value = methods[i].invoke(classNameObj);
                	if(value != null)
                		list.add(new NameValuePair(firstZM2LowerCase(methods[i].getName().replace("get", "")), value.toString()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.toArray(new NameValuePair[list.size()]);
    }
	
	private static String firstZM2LowerCase(String str){
		return str.substring(0,1).toLowerCase()+str.substring(1);
	}
	
}
