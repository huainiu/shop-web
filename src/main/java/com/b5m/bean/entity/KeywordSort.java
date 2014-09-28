package com.b5m.bean.entity;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Name;
import com.b5m.dao.annotation.Table;

@Table("t_keyword_sort")
public class KeywordSort {

	@Name
	private String name;

	@Column
	private String docid;

	public KeywordSort() {
	}

	public KeywordSort(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

}
