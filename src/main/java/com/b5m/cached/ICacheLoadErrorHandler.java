package com.b5m.cached;

/**
 * {@link ICacheable#load(Object...)}加载出错时，CachedManager将会调用这个接口。
 * @author Jacky Liu
 *
 */
public interface ICacheLoadErrorHandler {

	/**
	 * 
	 * @param cacheable 被加载的ICacheable接口。
	 * @param args 当时加载的参数。
	 */
	public void handleErrorLoading(ICacheable cacheable, Object... args);
}
