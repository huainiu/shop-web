package com.b5m.service.www.utils;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-7-25
 * @email echo.weng@b5m.com
 */
public class JsonUtilsTest {
	
	@Before
	public void setUp(){
		
	}
	
	@Test
	public void testIsEmpty(){
		String json = "[{}]";
		boolean actual = JsonUtils.isEmpty(json);
		boolean expected = true;
		Assert.assertEquals(expected, actual);
		
		json = "[{\"name\":\"zhang\"}]";
		actual = JsonUtils.isEmpty(json);
		expected = false;
		Assert.assertEquals(expected, actual);
	}
	
	@After
	public void tearDown(){
		
	}

}
