package com.b5m.bean.entity;

import java.util.Date;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Table;
import com.b5m.dao.domain.ColType;

@Table("seo_info")
public class Info extends DomainObject {

	@Column
	private String title;

	@Column(type = ColType.TEXT)
	private String content;

	@Column(name = "update_time")
	private Date updateTime;

	private String time;

	public Info() {
	}

	public Info(String title, String content, Date updateTime) {
		this.title = title;
		this.content = content;
		this.updateTime = updateTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
