package com.b5m.bean.dto.shoplist;

/**
 * 展现方式，例如列表为list,图片为image
 * @author jacky
 *
 */
public enum ShowType {

	List("list"), Image("image");
	
	final String value;
	
	ShowType(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
