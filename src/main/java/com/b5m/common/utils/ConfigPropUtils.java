package com.b5m.common.utils;

import java.util.Properties;

public class ConfigPropUtils {
	
	public static String getValue(String key){
		Properties config = SpringContextUtils.getBean("properties", Properties.class);
		return config.getProperty(key);
	}
	
	public static String getUcenterUrl(String key){
		Properties sysProperties = SpringContextUtils.getBean("sysConfig", Properties.class);
		String ucenterEnvironment = sysProperties.getProperty("ucenter.environment");
		String url = sysProperties.getProperty(key);
		return ucenterEnvironment + url;
	}
	
	public static String getPayUrl(String key){
		Properties sysProperties = SpringContextUtils.getBean("sysConfig", Properties.class);
		String payEnvironment = sysProperties.getProperty("pay.environment");
		String url = sysProperties.getProperty(key);
		return payEnvironment + url;
	}
	
	public static String getSysValue(String key){
		Properties sysProperties = SpringContextUtils.getBean("sysConfig", Properties.class);
		return sysProperties.getProperty(key);
	}
}
