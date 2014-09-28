package com.b5m.goods.promotions.dto;

import java.io.Serializable;

public class CPSChannelDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8286493933300746214L;

	private Integer id;
	
	private String name;
	
	private String link;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
}
