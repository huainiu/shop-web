package com.b5m.cached.template;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LocalCache {

	private final Map<String, Object> localCache = Collections
			.synchronizedMap(new HashMap<String, Object>());

	private final Map<String, Long> localCacheTimestamp = Collections
			.synchronizedMap(new HashMap<String, Long>());

	private final long expiredTime;
	
	/**
	 * 本地缓存的有效期的单位是否是以秒为单位，如果不是，则以毫秒为单位
	 */
	private boolean secTimeUnit = true;

	/**
	 * 需要将缓存的有效期放入进来。
	 * 
	 * @param expiredTime
	 */
	public LocalCache(long expiredTime) {
		this.expiredTime = expiredTime;
	}

	public void put(String key, Object value) {
		localCache.put(key, value);
		localCacheTimestamp.put(key, System.currentTimeMillis());
	}

	public void remove(String key) {
		if (!localCache.containsKey(key))
			return;
		localCache.remove(key);
		localCacheTimestamp.remove(key);
	}

	public boolean isSecTimeUnit() {
		return secTimeUnit;
	}

	public void setSecTimeUnit(boolean secTimeUnit) {
		this.secTimeUnit = secTimeUnit;
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
		long aliveTime = System.currentTimeMillis()
				- localCacheTimestamp.get(key);
		long __expiredTime = (secTimeUnit) ? expiredTime * 1000 : expiredTime;
		if (aliveTime >= __expiredTime) {
			remove(key);
			return null;
		}
		return type.cast(value);
	}

	public boolean containsKey(String key) {
		return localCache.containsKey(key);
	}
}
