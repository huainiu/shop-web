package com.b5m.goods.promotions.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SuppliserPromotionsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5628669424918637750L;
	
	/**
	 * 如果cpsInfo为空，并且promInfos和couponInfos的数量为0，则这个属性需要设置为false，其他，则为true
	 */
	private boolean containAnyPromotions = false;
	
	private CPSInfoDto cpsInfo;
	
	private List<PromInfoDto> promInfos;
	
	private List<CouponInfoDto> couponInfos;
	
	private SuppliserDto suppliser;

	public boolean isContainAnyPromotions() {
		return containAnyPromotions;
	}

	public void setContainAnyPromotions(boolean containAnyPromotions) {
		this.containAnyPromotions = containAnyPromotions;
	}

	public CPSInfoDto getCpsInfo() {
		return cpsInfo;
	}

	public void setCpsInfo(CPSInfoDto cpsInfo) {
		this.cpsInfo = cpsInfo;			
	}

	public List<PromInfoDto> getPromInfos() {
		if(null == promInfos)
			promInfos = new ArrayList<PromInfoDto>();
		return promInfos;
	}

	public void setPromInfos(List<PromInfoDto> promInfos) {
		this.promInfos = promInfos;
	}

	public List<CouponInfoDto> getCouponInfos() {
		if(null == couponInfos)
			couponInfos = new ArrayList<CouponInfoDto>();
		return couponInfos;
	}

	public void setCouponInfos(List<CouponInfoDto> couponInfos) {
		this.couponInfos = couponInfos;
	}

	public SuppliserDto getSuppliser() {
		return suppliser;
	}

	public void setSuppliser(SuppliserDto suppliser) {
		this.suppliser = suppliser;
	}
	
	
}
