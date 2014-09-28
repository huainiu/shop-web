package com.b5m.sf1.dto.req;
/**
 * description 
 *
 * @Company b5m
 * @author echo
 * @since 2014年2月18日
 */
public class AttrSearchBean {
    private String name;
    private String value;
    
    public AttrSearchBean(){}
    
    public AttrSearchBean(String name, String value){
    	this.name = name;
    	this.value = value;
    }
    
    public static AttrSearchBean newInstance(){
    	return new AttrSearchBean();
    }
    
    public static AttrSearchBean newInstance(String name, String value){
    	return new AttrSearchBean(name, value);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
