package com.b5m.goods.hotkeys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.b5m.goods.hotkeys.dto.HpSugGroupDto;
import com.b5m.goods.hotkeys.service.IHpSugGroupService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:com/b5m/goods/hotkeys/service/impl/applicationContext*.xml")
public class HpSugGroupServiceImplApp extends AbstractJUnit4SpringContextTests{

	@Resource
	private IHpSugGroupService groupService;
	
	@Test
	public void testGetHotKeys(){
		List<String> hotkeys = groupService.getHotKeywords();
		Assert.assertEquals(true, hotkeys.size() > 0);
	}
	
	@Test
	public void testFindByGroupname(){
		HpSugGroupDto dto = groupService.findByGroupName(HpSugGroupServiceImpl.GROUPNAME_RESULT_GOODS);
		Assert.assertNotNull(dto);
		Assert.assertEquals(true, !"".equals(dto.getContent()));
		
		dto = groupService.findByGroupName(HpSugGroupServiceImpl.GROUPNAME_RESULT_GOODS_LEFT);
		Assert.assertNotNull(dto);
		Assert.assertEquals(true, !"".equals(dto.getContent()));
	}
}
																																																									