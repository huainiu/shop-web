package com.b5m.client.daigoucart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DaigouCartLogUtils {
	
private static final Log LOG = LogFactory.getLog(DaigouCartLogUtils.class);
	
	public static void info(String message){
		LOG.info(message);
	}
	
	public static void error(String message){
		LOG.error(message);
	}
	
	public static void error(String message, Throwable e){
		LOG.error(message, e);
	}
	
}
