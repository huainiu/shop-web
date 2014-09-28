package com.b5m.goods.promotions.service;

import java.util.List;
import java.util.Map;

import com.b5m.goods.promotions.dto.CouponInfoDto;
import com.b5m.goods.promotions.dto.SuppliserDto;

public interface ICouponService {

	/**
	 * 返回所有有效的Coupon，有效的Coupon是指，当天的日期在Coupon的有效期范围之内。
	 * @return key:supplier name, value:coupon info
	 */
	public Map<String, List<CouponInfoDto>> findAllValidCoupon();
	
	public List<CouponInfoDto> findBySuppliser(SuppliserDto suppliser);
}
