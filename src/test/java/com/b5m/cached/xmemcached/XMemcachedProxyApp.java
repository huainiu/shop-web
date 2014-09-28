package com.b5m.cached.xmemcached;

import java.io.IOException;

import junit.framework.Assert;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.junit.Before;
import org.junit.Test;

import com.b5m.cached.exception.CachedException;
import com.b5m.cached.exception.ExistsKeyException;
import com.b5m.cached.exception.NonExistsKeyException;

/**
 * 此单元测试需要在本机安装memcached服务。
 * @author Jacky Liu
 *
 */
public class XMemcachedProxyApp {

	private XMemcachedProxy proxy;
	
	@Before
	public void setUp() throws IOException{
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(
				AddrUtil.getAddresses("localhost:11211"));
		MemcachedClient memcachedClient = builder.build();
		proxy = new XMemcachedProxy(memcachedClient);
		proxy.setWriteTimeout(1000);
		proxy.setRetriveTimeout(1000);
	}
	
	@Test
	public void testAdd() throws CachedException {
		String value = "Hello Jacky Liu";
		proxy.add("TestAdd", "Hello Jacky Liu", 10);
		Assert.assertEquals(value, proxy.get("TestAdd", String.class));
	}
	
	@Test(expected=ExistsKeyException.class)
	public void testRepeatAdd() throws CachedException{
		String value = "Hello Jacky Liu";
		proxy.add("TestRepeatAdd", value, 10);
		Assert.assertEquals(value, proxy.get("TestRepeatAdd", String.class));
		
		String value2 = "Hello Jacky Liu";
		proxy.add("TestRepeatAdd", value2, 10);
	}
	
	@Test(expected=NonExistsKeyException.class)
	public void testDelete() throws CachedException{
		String value = "Hello Jacky Liu";
		proxy.add("TestDelete", value, 10);
		proxy.delete("TestDelete");
		proxy.get("TestDelete");
	}
	
	@Test
	public void testPut() throws CachedException{
		String value = "Hello Jacky Liu";
		proxy.put("testPut", value, 10);
		Assert.assertEquals(value, proxy.get("testPut", String.class));
	}
	
	@Test
	public void testRepeatPut() throws CachedException{
		String value = "Hello Jacky Liu";
		proxy.put("testRepeatPut", value, 10);
		Assert.assertEquals(value, proxy.get("testRepeatPut", String.class));
		
		String value2 = "Hello World";
		proxy.put("testRepeatPut", value2, 10);
		Assert.assertEquals(value2, proxy.get("testRepeatPut", String.class));
	}
	
	@Test(expected=NonExistsKeyException.class)
	public void testExpiredTime() throws CachedException, InterruptedException{
		String value = "Hello Jacky Liu";
		// 有效期1秒钟
		proxy.add("testExpiredTime", value, 1);
		Thread.sleep(2000);
		proxy.get("testExpiredTime");
	}

}
