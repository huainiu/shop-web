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

import com.b5m.goods.promotions.dto.CPSInfoDto;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.service.ICPSService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:com/b5m/goods/promotions/service/impl/applicationContext*.xml")
public class CPSServiceImplApp extends AbstractJUnit4SpringContextTests{

	@Resource
	private ICPSService cpsService;
	
	@Test
	public void testFindSuppliserByCdt(){
		
	}
	
	@Test
	public void testFindBySuppliser(){
		SuppliserDto suppliserDto = new SuppliserDto();
		suppliserDto.setName("物美网");
		List<CPSInfoDto> cpsInfos = cpsService.findBySuppliser(suppliserDto);
		Assert.assertNotNull(cpsInfos);
		Assert.assertEquals(true, cpsInfos.size() > 0);
	}
	
	@Test
	public void testFindAllCPSInfo(){
		Map<String, CPSInfoDto> mapCps =  cpsService.findAllCPSInfo();
		Assert.assertEquals(true, mapCps.size() > 0);
		Set<String> keySet = mapCps.keySet();
		for(String key : keySet){
			CPSInfoDto cps = mapCps.get(key);
			Assert.assertNotNull(cps.getCommisionRatio());
			Assert.assertEquals(false, cps.getCommisionRatio().equals(""));
		}
	}
}
																																																									