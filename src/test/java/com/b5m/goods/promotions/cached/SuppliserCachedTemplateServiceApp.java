package com.b5m.goods.promotions.cached;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.dto.SuppliserPromotionsDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:com/b5m/goods/promotions/cached/dispatcher-servlet.xml")
public class SuppliserCachedTemplateServiceApp extends AbstractJUnit4SpringContextTests{

	@Resource
	private SuppliserCachedTemplateService service;
	
	@Before
	public void setUp(){
		org.apache.log4j.BasicConfigurator.configure();
	}
	
	@Test
	public void testFindAllSuppliser() {
		List<SuppliserDto> mapSuppliser = service.findAllSuppliser();
		Assert.assertEquals(true, mapSuppliser.size() > 0);
	}
	
	@Test
	public void testFindAllSuppliserPromotion(){
		Map<String, SuppliserPromotionsDto>  mapSuppliserProm = service.findAllSuppliserPromotion();
		Assert.assertEquals(true, mapSuppliserProm.size() > 0);
	}
	
	@Test
	public void testFindByCountry(){
		final String country = "韩国";
		List<SuppliserDto> supplisers = service.findByCountry(country);
		Assert.assertEquals(true, supplisers.size() > 0);
		for(SuppliserDto suppliser : supplisers){
			Assert.assertEquals(country, suppliser.getCountry());
		}
	}
	
	@Test
	public void testLoopLoadingTest(){
		final int num = 500;
		service.getCachedConfigure().setAlwaysRetriveFromRemote(false);
		for(int i = 0;i < num;i++){
			service.findAllSuppliserPromotion();
		}
	}

}
