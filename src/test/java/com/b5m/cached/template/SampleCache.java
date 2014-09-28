package com.b5m.cached.template;

import com.b5m.cached.CachedBase;
import com.b5m.cached.CachedConfigure;
import com.b5m.cached.ICachedProxy;
import com.b5m.cached.exception.CachedException;

public class SampleCache extends CachedBase {

	public SampleCache(ICachedProxy proxy, CachedConfigure configure) {
		super(proxy, configure);
	}

	@Override
	public boolean load(Object... args) throws CachedException {
		return true;
	}

	@Override
	public boolean unload(Object... args) throws CachedException {
		return true;
	}

}
