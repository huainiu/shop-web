package com.b5m.goods.promotions.service;

import java.util.List;
import java.util.Map;

import com.b5m.goods.promotions.dto.PromInfoDto;
import com.b5m.goods.promotions.dto.SuppliserDto;

/**
 * 
 * @author Jacky LIU
 *
 */
public interface IPromotionService {

	/**
	 * 找出所有的在有效期之内的优惠信息。
	 * @return key:supplier name, value:promotion info
	 */
	public Map<String, List<PromInfoDto>> findAllValidPromInfo();
	
	public List<PromInfoDto> findBySuppliser(SuppliserDto suppliser);
}
