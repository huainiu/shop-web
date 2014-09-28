package com.b5m.service.ontimeprice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OntimeLogUtils {
	
	private static final Log LOG = LogFactory.getLog(OntimeLogUtils.class);
	
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
