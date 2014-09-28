package com.b5m.goods.promotions.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class SuppliserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2908832487605063782L;

	private Integer id;
	
	private SuppliserGroupDto group;
	
	private String name;
	
	private String code;
	
	private String remark;
	
	private String logo;
	
	private String url;
	
	private String clientService;
	
	private String specialService;
	
	private String suppliserDesc;
	
	private String validState;
	
	private Integer serviceRate;
	
	private Integer transRate;
	
	private Integer sumRate;
	
	private Integer hotRate;
	
	private Integer commentSum;
	
	private String carriage;
	
	private String includeState;
	
	private int isMall;
	
	private int mallId;
	
	private String icon;
	
	private String country;
	
	private String broadbuy;
	
	private String payType;
	
	private String lanuage;
	
	private String mall;
	
	private boolean isKoreaSpl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SuppliserGroupDto getGroup() {
		return group;
	}

	public void setGroup(SuppliserGroupDto group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClientService() {
		return clientService;
	}

	public void setClientService(String clientService) {
		this.clientService = clientService;
	}

	public String getSpecialService() {
		return specialService;
	}

	public void setSpecialService(String specialService) {
		this.specialService = specialService;
	}

	public String getSuppliserDesc() {
		return suppliserDesc;
	}

	public void setSuppliserDesc(String suppliserDesc) {
		this.suppliserDesc = suppliserDesc;
	}

	public String getValidState() {
		return validState;
	}

	public void setValidState(String validState) {
		this.validState = validState;
	}

	public Integer getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(Integer serviceRate) {
		this.serviceRate = serviceRate;
	}

	public Integer getTransRate() {
		return transRate;
	}

	public void setTransRate(Integer transRate) {
		this.transRate = transRate;
	}

	public Integer getSumRate() {
		return sumRate;
	}

	public void setSumRate(Integer sumRate) {
		this.sumRate = sumRate;
	}

	public Integer getHotRate() {
		return hotRate;
	}

	public void setHotRate(Integer hotRate) {
		this.hotRate = hotRate;
	}

	public Integer getCommentSum() {
		return commentSum;
	}

	public void setCommentSum(Integer commentSum) {
		this.commentSum = commentSum;
	}

	public String getCarriage() {
		return carriage;
	}

	public void setCarriage(String carriage) {
		this.carriage = carriage;
	}

	public String getIncludeState() {
		return includeState;
	}

	public void setIncludeState(String includeState) {
		this.includeState = includeState;
	}
	
	public int getIsMall() {
		return isMall;
	}

	public void setIsMall(int isMall) {
		this.isMall = isMall;
	}

	public int getMallId() {
		return mallId;
	}

	public void setMallId(int mallId) {
		this.mallId = mallId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBroadbuy() {
		return broadbuy;
	}

	public void setBroadbuy(String broadbuy) {
		this.broadbuy = broadbuy;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getLanuage() {
		return lanuage;
	}

	public void setLanuage(String lanuage) {
		this.lanuage = lanuage;
	}

	public String getMall() {
		return mall;
	}

	public void setMall(String mall) {
		this.mall = mall;
	}
	
    public boolean getIsKoreaSpl(){
        return isKoreaSpl;
    }

    public void setIsKoreaSpl(boolean isKoreaSpl){
        this.isKoreaSpl = isKoreaSpl;
    }

    @Override
    public String toString()
    {
        return "SuppliserDto [id=" + id + ", group=" + group + ", name=" + name
                + ", code=" + code + ", remark=" + remark + ", logo=" + logo
                + ", url=" + url + ", clientService=" + clientService
                + ", specialService=" + specialService + ", suppliserDesc="
                + suppliserDesc + ", validState=" + validState
                + ", serviceRate=" + serviceRate + ", transRate=" + transRate
                + ", sumRate=" + sumRate + ", hotRate=" + hotRate
                + ", commentSum=" + commentSum + ", carriage=" + carriage
                + ", includeState=" + includeState + ", isMall=" + isMall
                + ", mallId=" + mallId + ", icon=" + icon + ", country="
                + country + ", broadbuy=" + broadbuy + ", payType=" + payType
                + ", lanuage=" + lanuage + ", mall=" + mall + ", isKoreaSpl="
                + isKoreaSpl + "]";
    }

}
