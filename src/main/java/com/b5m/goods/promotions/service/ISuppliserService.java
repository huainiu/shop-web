package com.b5m.goods.promotions.service;

import java.util.List;
import java.util.Map;

import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.dto.SuppliserPromotionsDto;

public interface ISuppliserService {
	
	/**
	 * 获取所有的商户信息.
	 * @return
	 */
	public List<SuppliserDto> findAllSuppliser();
	
	/**
	 * 获取所有的商户的促销信息
	 * @return
	 */
	public Map<String, SuppliserPromotionsDto> findAllSuppliserPromotion();
	
	/**
	 * 通过商户名称获取商户的促销信息。
	 * @param source 商户名称
	 * @return
	 */
	public SuppliserPromotionsDto findBySource(String source);
	
	/**
	 * 通过国家名称进行查找，例如搜索‘韩国’的商户
	 * @param country
	 * @return
	 */
	public List<SuppliserDto> findByCountry(String country);
	
	List<SuppliserDto> querySupplier(String keyword);
}
