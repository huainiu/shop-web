package com.b5m.common.utils;

import org.apache.commons.lang.StringUtils;

import com.b5m.cached.ICachedProxy;
import com.b5m.common.log.LogUtils;

/**
 * MemCached 工具类 备注： 还需优化配置文件
 * 
 * @author jianyuanyang
 * 
 */
public class MemCachedUtils {

	// 默认设置缓存有效时间 1 天 单位s
	public final static int DEFAULT_CACHE_TIME = 60 * 60 * 24;

	private static ICachedProxy proxy;

	/**
	 * 默认设置memcache 缓存
	 * 
	 * @param key 缓存的key
	 * @param value 缓存的value
	 * @return
	 */
	public static void setCache(String key, Object value) {
		setCache(key, value, DEFAULT_CACHE_TIME);
	}

	/**
	 * 设置memcache 缓存包括有效时间
	 * 
	 * @param key 缓存的key
	 * @param value 缓存的value
	 * @param exp 缓存的有效时间
	 * @return
	 */
	public static void setCache(String key, Object value, int exp) {
		if (StringUtils.isNotBlank(key)) {
			try {
				MemCachedUtils.proxy.put(key, value, exp);
			} catch (Exception e) {
				LogUtils.error(MemCachedUtils.class, e);
			}
		}
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param key 缓存的key
	 * @return value
	 */
	public static Object getCache(String key) {
		Object result = null;
		if (StringUtils.isNotBlank(key)) {
			for(int i = 0; i < 3; i++){//重复三次
				try {
					return MemCachedUtils.proxy.getMaybeNull(key);
				} catch (Exception e) {
					LogUtils.error(MemCachedUtils.class, e);
				}
			}
		}
		return result;
	}

	/**
	 * 清空某缓存对象
	 * 
	 * @param key 缓存的key
	 * @return
	 */
	public static void cleanCache(String key) {
		try {
			MemCachedUtils.proxy.delete(key);
		} catch (Exception e) {
			LogUtils.error(MemCachedUtils.class, e);
		}
	}

	
	public static ICachedProxy getProxy() {
		return proxy;
	}
 
	
	public void setProxy(ICachedProxy proxy) {
		MemCachedUtils.proxy = proxy;
	}
	
}
