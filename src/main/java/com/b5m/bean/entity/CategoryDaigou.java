package com.b5m.bean.entity;

import java.io.Serializable;
import java.util.Date;

import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Id;
import com.b5m.dao.annotation.Table;

@Table("t_category_daigou")
public class CategoryDaigou implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 672857649383046141L;

	@Id
	private Long id;
	
	@Column
	private String category;
	
	@Column
	private Date createTime;
	
	@Column
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
