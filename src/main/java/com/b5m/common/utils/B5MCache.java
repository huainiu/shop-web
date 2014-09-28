package com.b5m.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * description 
 *
 * @Company b5m
 * @author echo
 * @since 2013-5-27
 */
public final class B5MCache{
    public static final Map<String, Object> commonInfoMap = new HashMap<String, Object>();
    
    public static final String _BASE_PATH_KEY = "_BASE_PATH_KEY";
    
    public static final String _SERVER_KEY = "_SERVER_KEY";
    
    public static final String _UCENTER_HTTP_URL_KEY = "_UCENTER_HTTP_URL_KEY";
    
    public static final void putBasePath(String basePath){
        commonInfoMap.put(_BASE_PATH_KEY, basePath);
    }
    
    public static final void putServer(String server){
    	commonInfoMap.put(_SERVER_KEY, server);
    }
    
    public static final String getServer(){
    	Object server = commonInfoMap.get(_SERVER_KEY);
    	if(server == null) return null;
    	return server.toString();
    }
    
    public static final String getBasePath(){
        Object basePath = commonInfoMap.get(_BASE_PATH_KEY);
        if(basePath == null) return null;
        return basePath.toString();
    }
    
    public static final void putUcenterHttpUrl(String ucenterHttpUrl){
        commonInfoMap.put(_UCENTER_HTTP_URL_KEY, ucenterHttpUrl);
    }
    
    public static final String getUcenterHttpUrl(){
        Object ucenterHttpUrl = commonInfoMap.get(_UCENTER_HTTP_URL_KEY);
        if(ucenterHttpUrl == null) return null;
        return ucenterHttpUrl.toString();
    }
}
