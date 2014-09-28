package com.b5m.goods.promotions.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.b5m.goods.promotions.dto.CouponInfoDto;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.service.ICouponService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:com/b5m/goods/promotions/service/impl/applicationContext*.xml")
public class CouponServiceImplApp extends AbstractJUnit4SpringContextTests{

	@Resource
	private ICouponService couponService;
	
	@Test
	public void testFindSuppliserByCdt(){
		
	}
	
	@Test
	public void testFindBySuppliser(){
		SuppliserDto suppliserDto = new SuppliserDto();
		suppliserDto.setName("以纯");
		List<CouponInfoDto> coupons = couponService.findBySuppliser(suppliserDto);
		Assert.assertNotNull(coupons);
		// 目前没有测试数据
		//Assert.assertEquals(true, coupons.size() > 0);
	}
	
	@Test
	public void testFindAllValidCoupon(){
		Map<String, List<CouponInfoDto>> mapCoupon = couponService.findAllValidCoupon();
		Assert.assertEquals(true, mapCoupon.size() > 0);
		
		Set<String> keySet = mapCoupon.keySet();
		for(String key : keySet){
			List<CouponInfoDto> coupons = mapCoupon.get(key);
			String suppliser = null;
			for(CouponInfoDto coupon : coupons){
				if(suppliser == null){
					suppliser = coupon.getSuppliser().getName();
					continue;
				}
				Assert.assertEquals(suppliser, coupon.getSuppliser().getName());
			}
			
		}
	}
}
																																																									