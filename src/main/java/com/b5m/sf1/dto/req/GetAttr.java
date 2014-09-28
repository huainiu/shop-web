package com.b5m.sf1.dto.req;

/**
 * @author echo
 */
public class GetAttr {
	private Integer top = 20;
	private boolean isGetAttr = true;
	
	public GetAttr(){}
	
	public GetAttr(boolean isGetAttr, Integer top){
		this.isGetAttr = isGetAttr;
		this.top = top;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public boolean isGetAttr() {
		return isGetAttr;
	}

	public void setGetAttr(boolean isGetAttr) {
		this.isGetAttr = isGetAttr;
	}
	
}
