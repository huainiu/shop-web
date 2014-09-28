package com.b5m.bean.dto;

import java.util.Date;

public class Message {
	// msg.status
	public final static Integer UNREAD = 0;
	public final static Integer ALREAD = 1;
	public final static Integer REMOVE = 2;

	// msg.type
	public final static Integer NOTICE = 0;
	public final static Integer ANNOUNCEMENT = 1;

	private String recId;
	private String content;
	private String logo;
	private String title;
	private String fromUrl;
	private String fromStr;
	private Date time;
	private Integer type;
	private Integer status;
	private Long messageId;

	//发生验证code, 与业务无关
	private String token;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFromUrl() {
		return fromUrl;
	}

	public void setFromUrl(String fromUrl) {
		this.fromUrl = fromUrl;
	}

	public String getFromStr() {
		return fromStr;
	}

	public void setFromStr(String fromStr) {
		this.fromStr = fromStr;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
