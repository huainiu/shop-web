package com.b5m.common.spring.aop;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LocalCache {
	private final Map<String, Object> localCache = Collections.synchronizedMap(new HashMap<String, Object>());
	private final Map<String, Long> localCacheTimestamp = Collections.synchronizedMap(new HashMap<String, Long>());

	public void put(String key, Object value, long expiredTime) {
		localCache.put(key, value);
		localCacheTimestamp.put(key, System.currentTimeMillis() + expiredTime);
	}

	public void remove(String key) {
		if (!localCache.containsKey(key))
			return;
		localCache.remove(key);
		localCacheTimestamp.remove(key);
	}

	/**
	 * 如果本地缓存的数据过了有效期，那么获取的缓存数据将会是空。如果本地缓存没有数据，也会返回空
	 * 
	 * @param key
	 * @param type
	 * @return
	 */
	public <T> T get(String key, Class<T> type) {
		Object value = localCache.get(key);
		if (null == value)
			return null;
		long aliveTime = System.currentTimeMillis() - localCacheTimestamp.get(key);
		if (aliveTime >= 0) {
			remove(key);
			return null;
		}
		return type.cast(value);
	}
	
	/**
	 * 如果本地缓存的数据过了有效期，那么获取的缓存数据将会是空。如果本地缓存没有数据，也会返回空
	 * 
	 * @param key
	 * @param type
	 * @return
	 */
	public Object get(String key) {
		Object value = localCache.get(key);
		if (null == value)
			return null;
		long aliveTime = System.currentTimeMillis() - localCacheTimestamp.get(key);
		if (aliveTime >= 0) {
			remove(key);
			return null;
		}
		return value;
	}

	public boolean containsKey(String key) {
		return localCache.containsKey(key);
	}
	
	
}
