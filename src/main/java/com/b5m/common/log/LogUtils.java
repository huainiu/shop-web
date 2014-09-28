package com.b5m.common.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogUtils{
    public static void error(Class clazz, Exception e){
        Log log = LogFactory.getLog(clazz);
        StackTraceElement[] trace = e.getStackTrace();
        StringBuilder message = new StringBuilder();
        for (int i=0; i < trace.length; i++)
            message.append("\tat " + trace[i]);
        log.error(clazz.getSimpleName() + " error, error type is [" + e.getClass().getSimpleName() + "], error message[" + e.getMessage() + "], stack trace-->" + message);
    }
    
    public static void info(Class clazz, String messge){
    	Log log = LogFactory.getLog(clazz);
    	log.info(messge);
    }
    
    public static void debug(Class clazz, String messge){
    	Log log = LogFactory.getLog(clazz);
    	log.debug(messge);
    }
    
}
