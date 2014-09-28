package com.b5m.sf1.dto.req;
/**
 * description 
 *
 * @Company b5m
 * @author echo
 * @since 2014年2月18日
 */
public class GroupBean {
	private String property;
	private boolean range = false;
	
	public GroupBean(){}
	
	public GroupBean(String property, boolean range){
		this.property = property;
		this.range = range;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public boolean isRange() {
		return range;
	}

	public void setRange(boolean range) {
		this.range = range;
	}

}
