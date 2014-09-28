package com.b5m.bean.entity;

import java.io.Serializable;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Id;
import com.b5m.dao.annotation.Table;

@Table("t_hot_keywords")
public class HotKeywords implements Serializable{
	private static final long serialVersionUID = -1670745431062288371L;

	@Id
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column
	private String channel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
