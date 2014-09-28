package com.b5m.goods.promotions.dto;

import java.io.Serializable;

public class SuppliserGroupDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3450292198727788670L;

	private Integer id;
	
	private String name;

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
	
	
}
