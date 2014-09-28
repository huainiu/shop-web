package com.b5m.bean.dto;

public class Recharge {

	private String orderId;

	private String userId;

	private Double amount;

	private String eventName;

	private Integer eventId;

	private Integer type;

	private String memo;

	private String productName;

	private Integer productNum;

	public Recharge() {
		super();
	}

	public Recharge(String orderId, String userId, Double amount, String productName, Integer productNum, String memo) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.amount = amount;
		this.productName = productName;
		this.productNum = productNum;
		this.memo = memo;
	}

	public Recharge(String orderId, String userId, Double amount, String eventName, Integer eventId, Integer type, String memo) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.amount = amount;
		this.eventName = eventName;
		this.eventId = eventId;
		this.type = type;
		this.memo = memo;
	}

	public Recharge(String orderId, String userId, Integer eventId, Integer type) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.eventId = eventId;
		this.type = type;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

}