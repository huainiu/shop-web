package com.b5m.bean.entity;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Table;

@Table("search_hotkey")
public class SearchHotKey extends DomainObject {

	@Column
	private String name;
	
	
	public SearchHotKey() {
	}
	
	public SearchHotKey(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
