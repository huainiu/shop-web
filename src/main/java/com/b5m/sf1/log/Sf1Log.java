package com.b5m.sf1.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Sf1Log {
	public static void error(Exception e){
        Log log = LogFactory.getLog(Sf1Log.class);
        StackTraceElement[] trace = e.getStackTrace();
        StringBuilder message = new StringBuilder();
        for (int i=0; i < trace.length; i++)
            message.append("\tat " + trace[i]);
        log.error("error type is [" + e.getClass().getSimpleName() + "], error message[" + e.getMessage() + "], stack trace-->" + message);
    }
    
    public static void info(String messge){
    	Log log = LogFactory.getLog(Sf1Log.class);
    	log.info(messge);
    }
    
    public static void debug(String messge){
    	Log log = LogFactory.getLog(Sf1Log.class);
    	log.debug(messge);
    }
}
