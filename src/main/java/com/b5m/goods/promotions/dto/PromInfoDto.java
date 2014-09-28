package com.b5m.goods.promotions.dto;

import java.io.Serializable;
import java.sql.Date;

public class PromInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7256135251545302227L;

	private Long id;
	
	private Long groupId;
	
	private SuppliserDto suppliser;
	
	private Long brandId;
	
	private String name;
	
	private String image;
	
	private String link;
	
	private Date startTime;
	
	private Date endTime;
	
	private String feature;
	
	private String tag;
	
	private String top;
	
	private Date topTime;
	
	private Integer clickCount;
	
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public SuppliserDto getSuppliser() {
		return suppliser;
	}

	public void setSuppliser(SuppliserDto suppliser) {
		this.suppliser = suppliser;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public Date getTopTime() {
		return topTime;
	}

	public void setTopTime(Date topTime) {
		this.topTime = topTime;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
