package com.b5m.cached.template;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.b5m.cached.ICacheable;
import com.b5m.cached.exception.CachedException;

/**
 * {@link ICachedTemplate}基类
 * @author jacky
 *
 */
public abstract class CachedTemplateBase implements ICachedTemplate {
	protected final ICacheable cache;
	
	protected final Map<String, ISourceExtracter> extracters = Collections.synchronizedMap(new HashMap<String, ISourceExtracter>());
	
	public CachedTemplateBase(ICacheable cache){
		this.cache = cache;
	}

	@Override
	public ICacheable getCacheable() {
		return cache;
	}
	
	/**
	 * 将前缀加入到Key的前面，例如，前缀如果为sp，key为suppliser，那么返回的结果为"sp_suppliser"
	 * @param key
	 * @return
	 */
	protected String appendPrefixKey(String key){
		return new StringBuilder(cache.getCachedConfigure().getKeyPrefix())
				.append("_")
				.append(key).toString();
	}

	@Override
	public void addExtracter(String key, ISourceExtracter extracter){
		extracters.put(key, extracter);
	}
	
	public ISourceExtracter getExtracter(String key){
		return extracters.get(key);
	}

	protected abstract Object load(String key, ISourceExtracter extracter)
			throws CachedException ;
	
	protected abstract void unload(String key)
			throws CachedException;
}
