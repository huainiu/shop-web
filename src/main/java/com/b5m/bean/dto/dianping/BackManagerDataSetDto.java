package com.b5m.bean.dto.dianping;


public class BackManagerDataSetDto {
	private String keywords;
	
	private Long suppliserId;
	
	private Integer type;
	
	private String startDate;
	
	private String endDate;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Long getSuppliserId() {
		return suppliserId;
	}

	public void setSuppliserId(Long suppliserId) {
		this.suppliserId = suppliserId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
