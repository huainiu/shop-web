package com.b5m.bean.entity;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Table;

@Table("seo_links")
public class Link extends DomainObject {

	@Column
	private String link;

	@Column
	private String name;

	public Link() {
	}

	public Link(String link, String name) {
		super();
		this.link = link;
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
