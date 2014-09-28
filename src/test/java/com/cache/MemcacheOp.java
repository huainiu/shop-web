package com.cache;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.StringTools;
import com.b5m.bean.entity.filterAttr.Keywords;
import com.b5m.cached.exception.CachedException;
import com.b5m.dao.Dao;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.impl.DaoImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class MemcacheOp {
	private static String ADDRESS = "10.10.100.14";
	private static int PORT = 11311;
	
	//线上ip 10.10.109.3:11911     10.10.109.4:11911
	
	@SuppressWarnings("deprecation")
	@Test
	public void testCache() throws IOException, TimeoutException, InterruptedException, MemcachedException{//172.16.11.208:11211
		//测试
		XMemcachedClientBuilder builderTest = new XMemcachedClientBuilder(CollectionTools.newList(new InetSocketAddress(ADDRESS, 11311)));
//		XMemcachedClientBuilder builderTest = new XMemcachedClientBuilder(CollectionUtils.newList(new InetSocketAddress("10.10.99.208", 11211)));
//		10.10.99.208:11211
		//公网
//		57ee61bc33eb5732173eddd2b21ec648
		//10.10.100.11:11411   10.10.100.14:11311
//		10.10.100.14:11211 10.10.100.19:11411 10.10.100.55:11411
//		b5m_hotel_indexall1nullnullnull6nullnullnullASCnullnull-1list
//		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(CollectionTools.newList(new InetSocketAddress("10.10.100.14", 11211)));
//		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(CollectionTools.newList(new InetSocketAddress("10.10.100.11", 11311)));
		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(CollectionTools.newList(new InetSocketAddress("10.10.100.28", 11311)));
		MemcachedClient client = builder.build();//10.10.100.11：11311 //10.10.99.135:11211
//		tao_data_
//		client.set("v_version", 100000, new Date().getTime());
		
		/*2014新款春装
		毛呢外套
		连衣裙
		新款女鞋
		时尚女包
		女士衬衫
		秋冬连衣裙
		手机
		新款毛衣
		羽绒服
		毛呢大衣
		男鞋
		上装 女 冬2013新款
		打底裤
		女包
		手表
		十字绣
		2014春装新款连衣裙
		2014新款春装韩版
		羽绒服 女
		充电宝
		婚纱
		女针织衫
		毛呢外套 女
		皮草
		风衣 女
		2014春装新款女装*/
        
		
		String[] strs = new String[]{"2014新款春装", "毛呢外套", "连衣裙", "新款女鞋", "时尚女包", "女士衬衫", "秋冬连衣裙", "手机", "新款毛衣", "羽绒服", 
				"毛呢大衣", "男鞋", "上装 女 冬2013新款", "打底裤", "女包", "手表", "十字绣", "2014春装新款连衣裙", "2014新款春装韩版", "羽绒服 女", "充电宝", "婚纱", "女针织衫", "毛呢外套 女", "皮草", "风衣 女", "2014春装新款女装"};
		System.out.println(client.get("sp_all_supplisers"));
		for(String str : strs){
		}
		/*KeyIterator it = client.getKeyIterator(new InetSocketAddress("10.10.100.11", 11311));
		String[] keys = new String[]{"PriceReportServiceImpl"};
		while (it.hasNext()) {
			String key = it.next();
			for (String string : keys) {
				if (key.startsWith(string)) {
					client.delete(key);
				}
			}
		}*/
//		System.out.println(client.get(DigestUtils.md5Hex("PriceReportServiceImpl_getCategories_compare")));
//		client.delete(DigestUtils.md5Hex("PriceReportServiceImpl_getCategories_"+JSON.toJSONString("compare")+"_"));
//		client.delete(DigestUtils.md5Hex("PriceReportServiceImpl_getCategories_"+JSON.toJSONString("detail")+"_"));
//		clientTest.delete(DigestUtils.md5Hex("PriceReportServiceImpl_getCategories_"+JSON.toJSONString("compare")+"_"+JSON.toJSONString("201311")+"_"));
//		clientTest.delete(DigestUtils.md5Hex("PriceReportServiceImpl_getCategories_"+JSON.toJSONString("detail")+"_"+JSON.toJSONString("201311")+"_"));
//      client.delete(DigestUtils.md5Hex("SearchResultServiceImpl_articleInfo_"+JSON.toJSONString("ARTICLE_INFO")+"_"));
//      Object obj = client.get(DigestUtils.md5Hex("SearchResultServiceImpl_articleInfo_"+JSON.toJSONString("ARTICLE_INFO")+"_"));
	}
	
	@Test
	public void testClearCache() throws Exception{
		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(CollectionTools.newList(new InetSocketAddress("10.10.109.3", 11911)));
		MemcachedClient client = builder.build();
//		System.out.println(client.get("5026459ed41c5850c70162c8159de08d"));
//		System.out.println(client.get("2bc2cf81c533e5a2cb19a0e709b0028e"));
		client.delete("af31d9b5c4934123557d4e1530f73fb0");
	}
	
	@Test
	public void testTestMemcache() throws Exception{
	//	172.16.11.208:11211 172.16.11.208:11211 测试环境
		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(CollectionTools.newList(new InetSocketAddress("172.16.11.208", 11211)));
		MemcachedClient client = builder.build();
		System.out.println(client.delete("5026459ed41c5850c70162c8159de08d"));  
	}
	
	@Test
	public void testClearCache1(){
		System.out.println(getKey("N字鞋"));
		System.out.println(getKey("n字鞋"));
	}
	
	@Test
	public void testAdrsCacheZ() throws Exception{
//		10.10.100.28:11411 10.10.100.14:11311
//		172.16.11.208:11211
		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(CollectionTools.newList(new InetSocketAddress("172.16.11.208", 11211)));
		MemcachedClient client = builder.build();
		System.out.println(client.get("usercenter_status_731cadbb62ea4dfeb76d5ed554c543fb"));
	}
	
	@Test
	public void testGetKey(){
		/*System.out.println(getKey("毛呢外套"));*/
		System.out.println(StringTools.MD5("http://item.jd.com/1006222321.html"));
	}
	
	protected static String getKey(String name){
		return DigestUtils.md5Hex("SearchResultServiceImpl_queryAttrFilterList_\"" + name + "\"_");
	}
	
	@Test
	public void testCache1() throws Exception{
		//731cadbb62ea4dfeb76d5ed554c543fb
		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(CollectionTools.newList(new InetSocketAddress("10.10.100.11", 11211)));
		MemcachedClient client = builder.build();
		System.out.println(client.get("usercenter_status_731cadbb62ea4dfeb76d5ed554c543fb"));
	}
	
	
	public static void main(String[] args) throws IOException, CachedException, TimeoutException, InterruptedException, MemcachedException {
		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(CollectionTools.newList(new InetSocketAddress("10.10.99.135", 11211)));
		MemcachedClient client = builder.build();//10.10.100.11：11311 //10.10.99.135:11211
//		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(CollectionUtils.newList(new InetSocketAddress(ADDRESS, PORT)));
		List<String> keyCollections = new ArrayList<String>();
		keyCollections.add("2467");
		keyCollections.add("2466");
		keyCollections.add("2465");
		keyCollections.add("2464");
		keyCollections.add("2209");
		keyCollections.add("2212");
		System.out.println(client.get(keyCollections));
		//10.10.99.135:11211
		
		//sp_all_suppliser_proms//2903f99e5cf7460b98a22ab572fbcdc5
		/*System.out.println(client.get("sp_all_suppliser"));
		client.delete("sp_all_suppliser");
		client.delete("sp_all_suppliser_proms");*/
		//731cadbb62ea4dfeb76d5ed554c543fb
		/*client.add("test_key", 10, "value");
//		Thread.sleep(10);
		Long start = System.nanoTime();
		Object value = client.get("test_key");
		System.out.println(value);
		while(value != null){
			value = client.get("test_key");
		}
		System.out.println( (System.nanoTime() - start )/1000000000);
		System.out.println(client.get("test_key"));*/
		
//		System.out.println(client.get("Impress_1_All"));

		//144
//		System.out.println(client.get("Impress_144"));
		//"usercenter_key_" + "7e1e75fca78740bcb79c924ceaf69aa6"
		/*System.out.println(client.get("usercenter_status_731cadbb62ea4dfeb76d5ed554c543fb"));
		System.out.println(client.get("usercenter_key_" + "7e1e75fca78740bcb79c924ceaf69aa6"));*/
		
		/*for(int i = 1; i <= 10; i++){			
			client.delete("Comment_" + i + "_All");
			client.delete("Comment_" + i + "_0");
			client.delete("Comment_" + i + "_1");
			client.delete("Comment_" + i + "_2");
			client.delete("Impress_" + i + "_All");
		}*/
		
		//MemCachedManagerImpl.cacheSevers = "10.10.100.11:11311";
		/*System.out.println(client.delete("Comment_9_All"));
		System.out.println(client.delete("Comment_9_0"));
		System.out.println(client.delete("Comment_9_1"));
		System.out.println(client.delete("Comment_9_2"));*/
		/*System.out.println(client.delete("Comment_9_0"));
		System.out.println(client.delete("Comment_9_1"));
		System.out.println(client.delete("Comment_9_2"));*/
//		proxy.delete("sp_all_supplisers");
		
//		System.out.println(proxy.get("sp_all_supplisers"));
	}
}
