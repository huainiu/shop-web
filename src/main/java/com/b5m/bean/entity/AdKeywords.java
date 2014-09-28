package com.b5m.bean.entity;

import java.io.Serializable;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Id;
import com.b5m.dao.annotation.Table;

@Table("t_ad_keywords")
public class AdKeywords implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4882824694596324339L;

	@Id
	private Long id;

	@Column(name = "name")
	private String name;// 关键词

	@Column(name = "dd")
	private String dd;// 指定广告

	@Column(name = "position")
	private int position;// 位置

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

	public String getDd() {
		return dd;
	}

	public void setDd(String dd) {
		this.dd = dd;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
