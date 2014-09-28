package com.b5m.bean.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.b5m.base.common.utils.UrlTools;
import com.b5m.dao.annotation.Column;
import com.b5m.dao.annotation.Table;

@Table("suppliser")
public class Suppliser extends DomainObject {
	private String category;
	
	@Column
	private String name;
	
	@Column
	private String code;
	
	@Column
	private String logo;
	
	@Column
	private String url;
	
	private String remark;
	private String clientService;
	private String specialService;
	private String suppliserDesc;
	private String validState;
	private int serviceRate;
	private int transRate;
	private int sumRate;
	private int hotRate;
	private int commentSum;
	private String carriage;
	private int includeType;
	private String includeState;
	private int isMall;
	private int mallId;
	private String icon;
	private String country;
	private String broadbuy;
	private String payType;
	private String language;
	private String mall;
	private int commentNum;
	private int impressNum;
	private BigDecimal percent;
	
	@Column(name = "createTime")
	private Date createTime;
	
	@Column(name = "updateTime")
	private Date updateTime;
	
	private int goodPinNum;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEncodeName(){
		if(StringUtils.isEmpty(name)) return name;
		return UrlTools.urlEncode(name);
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

	public int getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(int serviceRate) {
		this.serviceRate = serviceRate;
	}

	public int getTransRate() {
		return transRate;
	}

	public void setTransRate(int transRate) {
		this.transRate = transRate;
	}

	public int getSumRate() {
		return sumRate;
	}

	public void setSumRate(int sumRate) {
		this.sumRate = sumRate;
	}

	public int getHotRate() {
		return hotRate;
	}

	public void setHotRate(int hotRate) {
		this.hotRate = hotRate;
	}

	public int getCommentSum() {
		return commentSum;
	}

	public void setCommentSum(int commentSum) {
		this.commentSum = commentSum;
	}

	public String getCarriage() {
		return carriage;
	}

	public void setCarriage(String carriage) {
		this.carriage = carriage;
	}

	public int getIncludeType() {
		return includeType;
	}

	public void setIncludeType(int includeType) {
		this.includeType = includeType;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMall() {
		return mall;
	}

	public void setMall(String mall) {
		this.mall = mall;
	}

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	public int getImpressNum() {
		return impressNum;
	}

	public void setImpressNum(int impressNum) {
		this.impressNum = impressNum;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public int getGoodPinNum() {
		return goodPinNum;
	}

	public void setGoodPinNum(int goodPinNum) {
		this.goodPinNum = goodPinNum;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((broadbuy == null) ? 0 : broadbuy.hashCode());
		result = prime * result + ((carriage == null) ? 0 : carriage.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((clientService == null) ? 0 : clientService.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + commentNum;
		result = prime * result + commentSum;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + goodPinNum;
		result = prime * result + hotRate;
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + impressNum;
		result = prime * result + ((includeState == null) ? 0 : includeState.hashCode());
		result = prime * result + includeType;
		result = prime * result + isMall;
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((logo == null) ? 0 : logo.hashCode());
		result = prime * result + ((mall == null) ? 0 : mall.hashCode());
		result = prime * result + mallId;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((payType == null) ? 0 : payType.hashCode());
		result = prime * result + ((percent == null) ? 0 : percent.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + serviceRate;
		result = prime * result + ((specialService == null) ? 0 : specialService.hashCode());
		result = prime * result + sumRate;
		result = prime * result + ((suppliserDesc == null) ? 0 : suppliserDesc.hashCode());
		result = prime * result + transRate;
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((validState == null) ? 0 : validState.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Suppliser other = (Suppliser) obj;
		if (broadbuy == null) {
			if (other.broadbuy != null)
				return false;
		} else if (!broadbuy.equals(other.broadbuy))
			return false;
		if (carriage == null) {
			if (other.carriage != null)
				return false;
		} else if (!carriage.equals(other.carriage))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (clientService == null) {
			if (other.clientService != null)
				return false;
		} else if (!clientService.equals(other.clientService))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (commentNum != other.commentNum)
			return false;
		if (commentSum != other.commentSum)
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (goodPinNum != other.goodPinNum)
			return false;
		if (hotRate != other.hotRate)
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (impressNum != other.impressNum)
			return false;
		if (includeState == null) {
			if (other.includeState != null)
				return false;
		} else if (!includeState.equals(other.includeState))
			return false;
		if (includeType != other.includeType)
			return false;
		if (isMall != other.isMall)
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (logo == null) {
			if (other.logo != null)
				return false;
		} else if (!logo.equals(other.logo))
			return false;
		if (mall == null) {
			if (other.mall != null)
				return false;
		} else if (!mall.equals(other.mall))
			return false;
		if (mallId != other.mallId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (payType == null) {
			if (other.payType != null)
				return false;
		} else if (!payType.equals(other.payType))
			return false;
		if (percent == null) {
			if (other.percent != null)
				return false;
		} else if (!percent.equals(other.percent))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (serviceRate != other.serviceRate)
			return false;
		if (specialService == null) {
			if (other.specialService != null)
				return false;
		} else if (!specialService.equals(other.specialService))
			return false;
		if (sumRate != other.sumRate)
			return false;
		if (suppliserDesc == null) {
			if (other.suppliserDesc != null)
				return false;
		} else if (!suppliserDesc.equals(other.suppliserDesc))
			return false;
		if (transRate != other.transRate)
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (validState == null) {
			if (other.validState != null)
				return false;
		} else if (!validState.equals(other.validState))
			return false;
		return true;
	}

}
