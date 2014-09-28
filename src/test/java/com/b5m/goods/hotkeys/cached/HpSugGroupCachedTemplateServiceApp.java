package com.b5m.goods.hotkeys.cached;

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

import com.b5m.goods.hotkeys.dto.HpSugGroupDto;
import com.b5m.goods.hotkeys.service.impl.HpSugGroupServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:com/b5m/goods/hotkeys/cached/dispatcher-servlet-2.xml")
public class HpSugGroupCachedTemplateServiceApp extends AbstractJUnit4SpringContextTests{

	@Resource
	private HpSugGroupCachedTemplateService service;
	
	@Before
	public void setUp(){
		org.apache.log4j.BasicConfigurator.configure();
	}
	
	@Test
	public void testGetHotkeywords() {
		List<String> hotkeywords = service.getHotKeywords();
		Assert.assertEquals(true, hotkeywords.size() > 0);
	}
	
	@Test
	public void testFindAll(){
		Map<String, HpSugGroupDto> groups = service.findAll();
		Assert.assertEquals(true, groups.size() > 0);
		Assert.assertEquals(true, groups.containsKey(HpSugGroupServiceImpl.GROUPNAME_RESULT_GOODS));
		Assert.assertEquals(true, groups.containsKey(HpSugGroupServiceImpl.GROUPNAME_HOT_KEY_HOME));
		Assert.assertEquals(true, groups.containsKey(HpSugGroupServiceImpl.GROUPNAME_RESULT_GOODS_LEFT));
	}
	
	@Test
	public void testFindByGroupName(){
		HpSugGroupDto group = service.findByGroupName(HpSugGroupServiceImpl.GROUPNAME_RESULT_GOODS);
		Assert.assertNotNull(group);
	}
	
	@Test
	public void loadingTest(){
		final int count = 10000;
		for(int i = 0;i < count;i++){
			List<String> hotkeywords = service.getHotKeywords();
			Assert.assertEquals(true, hotkeywords.size() > 0);
		}
	}
}
