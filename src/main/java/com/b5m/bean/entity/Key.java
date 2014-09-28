package com.b5m.bean.entity;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Table;

@Table("seo_key")
public class Key extends DomainObject {

	@Column
	private String name;

	public Key() {
	}

	public Key(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
