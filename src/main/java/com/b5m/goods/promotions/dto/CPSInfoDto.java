package com.b5m.goods.promotions.dto;

import java.io.Serializable;

public class CPSInfoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5637021980318122000L;

	private Integer id;
	
	private CPSChannelDto channel;
	
	private SuppliserDto suppliser;
	
	private String commisionRatio;
	
	private Long actionId;

	private String link;
	
	private String customLink;
	
	private String description;
	
	private String state;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SuppliserDto getSuppliser() {
		return suppliser;
	}

	public void setSuppliser(SuppliserDto suppliser) {
		this.suppliser = suppliser;
	}

	public String getCommisionRatio() {
		return commisionRatio;
	}

	public void setCommisionRatio(String commisionRatio) {
		this.commisionRatio = commisionRatio;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCustomLink() {
		return customLink;
	}

	public void setCustomLink(String customLink) {
		this.customLink = customLink;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public CPSChannelDto getChannel() {
		return channel;
	}

	public void setChannel(CPSChannelDto channel) {
		this.channel = channel;
	}
}
