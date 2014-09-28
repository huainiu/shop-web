package com.b5m.cached.template;

import com.b5m.cached.CachedConfigure;
import com.b5m.cached.ICacheable;
import com.b5m.cached.exception.CachedException;

/**
 * CachedTemplate用于封装Cached常用调用方法，例如load或者取数据等等。
 * @author jacky
 *
 */
public interface ICachedTemplate {

	/**
	 * 添加提取器，之后{@link #load(Object...)}方法将会执行已添加的提取器
	 * @param key
	 * @param extracter
	 */
	void addExtracter(String key, ISourceExtracter extracter);
	
	/**
	 * 加载添加的提取器
	 * @param args
	 * @throws CachedException
	 */
	void load(Object... args)throws CachedException;
	
	/**
	 * 卸载缓存
	 * @param args
	 * @throws CachedException
	 */
	void unload(Object... args) throws CachedException;
	
	/**
	 * 从缓存中获取，缓存可能是本地内存缓存或者是远程的缓存，这个需要根据{@link CachedConfigure#isAlwaysRetriveFromRemote()}
	 * 进行判断。
	 * @param key
	 * @param type
	 * @param args TODO
	 * @return
	 */
	<T> T get(String key, Class<T> type, Object... args) throws CachedException;
	
	<T> T get(String key, Class<T> type) throws CachedException;
	
	/**
	 * 从本地内存中获取，如果没有，则返回null
	 * @param key
	 * @return
	 */
	<T> T getFromLocal(String key, Class<T> type) throws CachedException;
	
	/**
	 * 从缓存中获取
	 * @param key
	 * @param type
	 * @return
	 */
	<T> T getFromRemote(String key, Class<T> type) throws CachedException;
	
	/**
	 * 因为getFromRemote 这个方法如果memcache中 不存在这个key 就会报错，个人觉得这点设计很不好，利用异常来处理总是影响性能的
	 * @description
	 *
	 * @param key
	 * @param type
	 * @return
	 * @throws CachedException
	 * @return T
	 * @date 2013-6-22
	 * @author xiuqing.weng
	 */
	<T> T getFromRemoteMayBeNull(String key, Class<T> type) throws CachedException;
	
	/**
	 * 获取缓存接口
	 * @return
	 */
	ICacheable getCacheable();
}
