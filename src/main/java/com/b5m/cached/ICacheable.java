package com.b5m.cached;

import com.b5m.cached.exception.CachedException;

/**
 * 可缓存的对象接口，当衍生类实现此接口时
 * @author Jacky Liu
 *
 */
public interface ICacheable {
	
	/**
	 * 获取可缓存对象的配置。
	 * @return
	 */
	public CachedConfigure getCachedConfigure();
	
	/**
	 * 获取缓存客户端代理。
	 * @return
	 */
	public ICachedProxy getCachedProxy();
	
	/**
	 * 加载缓存，用于缓存数据的预先加载。
	 * @param args 加载参数
	 * @return 如果加载成功，则返回true
	 */
	public boolean load(Object... args) throws CachedException;
	
	public boolean unload(Object... args) throws CachedException;
}
