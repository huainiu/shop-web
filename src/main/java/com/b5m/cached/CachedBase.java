package com.b5m.cached;

public abstract class CachedBase implements ICacheable {

	protected final CachedConfigure configure;
	
	protected final ICachedProxy proxy;
	
	/**
	 * 加载标志
	 */
	protected volatile boolean loaded = false;
	
	public CachedBase(ICachedProxy proxy, CachedConfigure configure){
		this.configure = configure;
		this.proxy = proxy;
	}
	
	@Override
	public CachedConfigure getCachedConfigure() {
		return this.configure;
	}

	public boolean isLoaded() {
		return loaded;
	}

	@Override
	public ICachedProxy getCachedProxy() {
		return proxy;
	}

	
}
