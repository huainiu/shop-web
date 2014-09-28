package com.b5m.common.utils;
import net.spy.memcached.MemcachedClient;
import org.apache.commons.lang.StringUtils;


/**
 * MemCached 工具类   备注： 还需优化配置文件
 * @author jianyuanyang
 *
 */
public class UcMemCachedUtils {
	
	//默认设置缓存有效时间 1 天 单位s
	public final static int DEFAULT_CACHE_TIME = 60 * 60 * 24; 
	
	private static UcMemCachedUtils _this=null;
	
	//private  static MemcachedClient memcached ; 
	
	private static MemcachedClient memcachedClient;
	
	
   
	/**
	 * 初始化 MemCacheUtil 单例 对象
	 * @return
	 */
	public static synchronized UcMemCachedUtils getInstance(){
		if(_this==null){
			_this=new UcMemCachedUtils();
			_this.getMemCacheInstance();
		}
		return _this;
	}
	
	
	/**
	 * 利用 spring 上下文 根据beanName 获取memCahcheClient 实体
	 */
	public void getMemCacheInstance(){		
		if(null == memcachedClient){
			//memcachedClient = (MemcachedClient)ContextUtils.getBean("memcachedClient");
			memcachedClient = (MemcachedClient)ContextUtils.getBean("ucMemclient");
		}		
	}
	
	
	/*static  {
			if(null == memcachedClient){
				try {
					memcachedClient = new MemcachedClient(new InetSocketAddress("10.10.99.135", 11211));
				} catch (IOException e) {				
					e.printStackTrace();
				}
		   }
	}*/
	
	/**
	 * 默认设置memcache 缓存 
	 * @param key
	 *           缓存的key
	 * @param value
	 *           缓存的value   
	 * @return
	 */
	public static void setCache(String key, Object value) {
		 setCache(key,value,DEFAULT_CACHE_TIME);
	}

	/**
	 * 设置memcache 缓存包括有效时间
	 * @param key
	 *           缓存的key
	 * @param value
	 *           缓存的value
	 * @param exp
	 *           缓存的有效时间
	 * @return  
	 */
	public static void setCache(String key, Object value, int exp) {
		if (StringUtils.isNotBlank(key)) {	
			try{
			  UcMemCachedUtils.getInstance().memcachedClient.set(key,exp,value);
			}
		   catch(Exception e){
		      e.printStackTrace(); 	
		    }
		}
	}

	
	/**
	 * 获取缓存对象
	 * 
	 * @param key
	 *           缓存的key
	 * @return  value
	 */
	@SuppressWarnings("static-access")
	public static Object getCache(String key) {
		Object result = null;
		if (StringUtils.isNotBlank(key)) {	
			result = UcMemCachedUtils.getInstance().memcachedClient.get(key);
		}
		return result;
	}

	/**
	 * 清空某缓存对象
	 * 
	 * @param key
	 *          缓存的key
	 * @return
	 */
	public static void cleanCache(String key) {		
		UcMemCachedUtils.getInstance().memcachedClient.delete(key);		
	}

	/**
	 * 清空所有缓存对象 
	 * @return
	 */
	public static void cleanAllRemoteCache() {
		UcMemCachedUtils.getInstance().memcachedClient.flush();
	}
	

}
