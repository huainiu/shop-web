package com.b5m.bean.entity;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Table;

@Table("category")
public class Category extends DomainObject {
	@Column
	private String context;

	
	public String getContext() {
		return context;
	}

	
	public void setContext(String context) {
		this.context = context;
	}
	
}
