package com.b5m.cached.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.junit.Before;
import org.junit.Test;

import com.b5m.cached.CachedConfigure;
import com.b5m.cached.exception.CachedException;
import com.b5m.cached.exception.NonExistsKeyException;
import com.b5m.cached.xmemcached.XMemcachedProxy;
import com.b5m.common.spring.interceptor.B5MInterceptorAdapter;
import com.b5m.common.utils.UcMemCachedUtils;

/**
 * 这个测试用例需要本地安装memcached服务才能运行
 * @author jacky
 *
 */
public class SimpleCachedTemplateApp {
	private ICachedTemplate template;
	
	@Before
	public void setUp() throws IOException{
		org.apache.log4j.BasicConfigurator.configure();
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(
				AddrUtil.getAddresses("localhost:11211"));
		MemcachedClient memcachedClient = builder.build();
		XMemcachedProxy proxy = new XMemcachedProxy(memcachedClient);
		proxy.setWriteTimeout(2000);
		proxy.setRetriveTimeout(2000);
		CachedConfigure config = new CachedConfigure();
		config.setAlwaysRetriveFromRemote(true);
		config.setExpiredTime(10000);
		config.setKeyPrefix("sp");
		SampleCache cache = new SampleCache(proxy, config);
		template = new SimpleCachedTemplate(cache);
	}
	
	@Test
	public void aaaa() {
		UcMemCachedUtils.getCache(B5MInterceptorAdapter.USER_KEY_PREFIX+"");
	}
	
	/**
	 * 测试单独一个ISourceExtracter的加载功能
	 * @throws CachedException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testSingleLoad() throws CachedException {
		final String key = "sample1";
		template.addExtracter(key, new ISourceExtracter(){

			@Override
			public Object extract(Object... args) {
				List<String> testData = new ArrayList<String>();
				testData.add("data1");
				testData.add("data2");
				testData.add("data3");
				testData.add("data4");
				testData.add("data5");
				testData.add("data6");
				return testData;
			}
			
		});
		template.load();
		
		List<String> data = template.getFromLocal(key, List.class);
		Assert.assertNull(data);
		data = template.getFromRemote(key, List.class);
		Assert.assertNotNull(data);
		Assert.assertEquals(6, data.size());
		
		data = template.get(key, List.class, null);
		Assert.assertNotNull(data);
		Assert.assertEquals(6, data.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=NonExistsKeyException.class)
	public void testUnload() throws CachedException{
		final String key = "sampleUnload";
		template.addExtracter(key, new ISourceExtracter(){

			@Override
			public Object extract(Object... args) {
				List<String> testData = new ArrayList<String>();
				testData.add("data1");
				testData.add("data2");
				testData.add("data3");
				testData.add("data4");
				testData.add("data5");
				testData.add("data6");
				return testData;
			}
			
		});
		template.load();
		List<String> data = template.getFromLocal(key, List.class);
		Assert.assertNull(data);
		data = template.getFromRemote(key, List.class);
		Assert.assertNotNull(data);
		Assert.assertEquals(6, data.size());
		
		template.unload();
		data = template.getFromRemote(key, List.class);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMultiLoad() throws CachedException{
		final String key = "sample1";
		final String key2 = "sample2";
		final String key3 = "sample3";
		
		template.addExtracter(key, new ISourceExtracter(){

			@Override
			public Object extract(Object... args) {
				List<String> testData = new ArrayList<String>();
				testData.add("data1");
				testData.add("data2");
				testData.add("data3");
				testData.add("data4");
				testData.add("data5");
				testData.add("data6");
				return testData;
			}
			
		});
		
		template.addExtracter(key2, new ISourceExtracter(){

			@Override
			public Object extract(Object... args) {
				List<String> testData = new ArrayList<String>();
				testData.add("data1");
				testData.add("data2");
				testData.add("data3");
				testData.add("data4");
				testData.add("data5");
				return testData;
			}
			
		});
		
		template.addExtracter(key3, new ISourceExtracter(){

			@Override
			public Object extract(Object... args) {
				List<String> testData = new ArrayList<String>();
				testData.add("data1");
				testData.add("data2");
				testData.add("data3");
				testData.add("data4");
				return testData;
			}
			
		});
		
		
		template.load();
		
		List<String> data = template.getFromLocal(key, List.class);
		Assert.assertNull(data);
		data = template.getFromRemote(key, List.class);
		Assert.assertNotNull(data);
		Assert.assertEquals(6, data.size());
		
		data = template.get(key, List.class, null);
		Assert.assertNotNull(data);
		Assert.assertEquals(6, data.size());

		data = template.getFromLocal(key2, List.class);
		Assert.assertNull(data);
		data = template.getFromRemote(key2, List.class);
		Assert.assertNotNull(data);
		Assert.assertEquals(5, data.size());
		
		data = template.get(key2, List.class, null);
		Assert.assertNotNull(data);
		Assert.assertEquals(5, data.size());

		data = template.getFromLocal(key3, List.class);
		Assert.assertNull(data);
		data = template.getFromRemote(key3, List.class);
		Assert.assertNotNull(data);
		Assert.assertEquals(4, data.size());
		
		data = template.get(key3, List.class, null);
		Assert.assertNotNull(data);
		Assert.assertEquals(4, data.size());		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEnableLocalCached() throws CachedException{
		final String key = "sample1";
		// 通过设置alwaysRetriveFromRemote为false，打开本地缓存的功能
		template.getCacheable().getCachedConfigure().setAlwaysRetriveFromRemote(false);
		template.addExtracter(key, new ISourceExtracter(){

			@Override
			public Object extract(Object... args) {
				List<String> testData = new ArrayList<String>();
				testData.add("data1");
				testData.add("data2");
				testData.add("data3");
				testData.add("data4");
				testData.add("data5");
				testData.add("data6");
				return testData;
			}
			
		});
		template.load();
		
		List<String> data = template.getFromLocal(key, List.class);
		Assert.assertNotNull(data);
		Assert.assertEquals(6, data.size());
		data = template.get(key, List.class, null);
		Assert.assertEquals(6, data.size());
	}

	/**
	 * 打开本地缓存，并测试有效期
	 * @throws CachedException 
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unchecked")
	@Test(expected=NonExistsKeyException.class)
	public void testEnableLocalCachedExpired() throws CachedException, InterruptedException{
		final String key = "sample10";
		// 通过设置alwaysRetriveFromRemote为false，打开本地缓存的功能
		template.getCacheable().getCachedConfigure().setAlwaysRetriveFromRemote(false);
		// 设置1秒的超时时间
		template.getCacheable().getCachedConfigure().setExpiredTime(1);
		template.addExtracter(key, new ISourceExtracter(){

			@Override
			public Object extract(Object... args) {
				List<String> testData = new ArrayList<String>();
				testData.add("data1");
				testData.add("data2");
				testData.add("data3");
				testData.add("data4");
				testData.add("data5");
				testData.add("data6");
				return testData;
			}
			
		});
		template.load();
		
		List<String> data = template.getFromLocal(key, List.class);
		Assert.assertNotNull(data);
		Assert.assertEquals(6, data.size());
		data = template.getFromRemote(key, List.class);
		Assert.assertEquals(6, data.size());
		
		// 休眠1秒钟
		Thread.sleep(1000);
		data = template.getFromLocal(key, List.class);
		Assert.assertNull(data);
		template.getFromRemote(key, List.class);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEnableLocalCachedExpired2() throws CachedException, InterruptedException{
		final String key = "sample10";
		// 通过设置alwaysRetriveFromRemote为false，打开本地缓存的功能
		template.getCacheable().getCachedConfigure().setAlwaysRetriveFromRemote(false);
		// 设置1秒的超时时间
		template.getCacheable().getCachedConfigure().setExpiredTime(1);
		template.addExtracter(key, new ISourceExtracter(){

			@Override
			public Object extract(Object... args) {
				List<String> testData = new ArrayList<String>();
				testData.add("data1");
				testData.add("data2");
				testData.add("data3");
				testData.add("data4");
				testData.add("data5");
				testData.add("data6");
				return testData;
			}
			
		});
		template.load();
		
		List<String> data = template.getFromLocal(key, List.class);
		Assert.assertNotNull(data);
		Assert.assertEquals(6, data.size());
		data = template.getFromRemote(key, List.class);
		Assert.assertEquals(6, data.size());
		
		// 休眠1秒钟
		Thread.sleep(1000);
		data = template.getFromLocal(key, List.class);
		Assert.assertNull(data);
		// 如果过了有效期，template将会自动调用extracter，并将数据加载到本地和远程
		data = template.get(key, List.class, null);
		Assert.assertNotNull(data);
		Assert.assertEquals(6, data.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void loadingTest() throws CachedException{
		final String key = "sample10";
		// 通过设置alwaysRetriveFromRemote为false，打开本地缓存的功能
		template.getCacheable().getCachedConfigure().setAlwaysRetriveFromRemote(false);
		// 设置1秒的超时时间
		template.getCacheable().getCachedConfigure().setExpiredTime(2);
		template.addExtracter(key, new ISourceExtracter(){

			@Override
			public Object extract(Object... args) {
				List<String> testData = new ArrayList<String>();
				testData.add("data1");
				testData.add("data2");
				testData.add("data3");
				testData.add("data4");
				testData.add("data5");
				testData.add("data6");
				return testData;
			}
			
		});
		template.load();
		final int count = 10000000;
		for(int i = 0;i < count;i++){
			List<String> data = template.get(key, List.class, null);
			Assert.assertNotNull(data);
			Assert.assertEquals(6, data.size());
		}
	}
}
