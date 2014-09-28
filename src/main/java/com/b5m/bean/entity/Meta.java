package com.b5m.bean.entity;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Table;

@Table("seo_meta")
public class Meta extends DomainObject {

	@Column(name = "page_name")
	private String pageName;

	@Column
	private String title;

	@Column
	private String des;

	@Column
	private String keyword;

	public Meta() {
	}

	public Meta(String pageName, String title, String des, String keyword) {
		super();
		this.pageName = pageName;
		this.title = title;
		this.des = des;
		this.keyword = keyword;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
