package com.b5m.goods.promotions.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.b5m.goods.promotions.dto.PromInfoDto;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.service.IPromotionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:com/b5m/goods/promotions/service/impl/applicationContext*.xml")
public class PromotionServiceImplApp extends AbstractJUnit4SpringContextTests{

	@Resource
	private IPromotionService promotionService;
	
	@Test
	public void testFindSuppliserByCdt(){
		
	}
	
	@Test
	public void testFindPromInfosBySuppliser(){
		SuppliserDto suppliserDto = new SuppliserDto();
		suppliserDto.setName("京东商城");
		@SuppressWarnings("unused")
		List<PromInfoDto> infos = promotionService.findBySuppliser(suppliserDto);
		// TODO 目前没有测试数据
		//Assert.assertEquals(true, infos.size() > 0);
	}
	
	@Test
	public void testFindAllValidPromInfo(){
		Map<String, List<PromInfoDto>> mapPromInfo = promotionService.findAllValidPromInfo();
		// TODO 目前没有测试数据
		//Assert.assertEquals(true, mapPromInfo.size() > 0);
		Set<String> keySet = mapPromInfo.keySet();
		for(String key : keySet){
			List<PromInfoDto> proms = mapPromInfo.get(key);
			String suppliser = null;
			for(PromInfoDto prom : proms){
				if(suppliser == null){
					suppliser = prom.getSuppliser().getName();
					continue;
				}
				Assert.assertEquals(suppliser, prom.getSuppliser().getName());
			}
			
		}
	}
}
																																																									