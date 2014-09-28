package com.b5m.sf1.dto.req;

/**
 * description 
 *
 * @Company b5m
 * @author echo
 * @since 2014年2月18日
 */
public class SelectSearchBean {
    /**
     * scd字段名
     */
    private String scdName = "";
    
    /**
     * 业务字段名
     */
    private String appName = "";
    
    public SelectSearchBean(){}
    
    public SelectSearchBean(String scdName, String appName){
    	this.scdName = scdName;
    	this.appName = appName;
    }

    public String getScdName() {
        return scdName;
    }

    public void setScdName(String scdName) {
        this.scdName = scdName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}