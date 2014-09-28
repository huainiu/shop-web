package com.b5m.cached.template;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.b5m.cached.ICacheable;
import com.b5m.cached.exception.CachedException;
import com.b5m.cached.exception.NonExistsKeyException;

/**
 * 简单易用的缓存模板，他的本地缓存使用的是{@link ConcurrentHashMap}
 * @author jacky
 *
 */
public class SimpleCachedTemplate extends CachedTemplateBase {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private LocalCache localCache;
	
	private final Lock lock = new ReentrantLock();
	
	/**
	 * 加载标志
	 */
	protected volatile boolean loaded = false;
	
	public SimpleCachedTemplate(ICacheable cache) {
		super(cache);
	}
	
	protected LocalCache getLocalCache(){
		if(null == localCache)
			localCache = new LocalCache(super.getCacheable().getCachedConfigure().getExpiredTime());
	
		return localCache;
	}
	
	/**
	 * 本地缓存的有效期的单位是否是以秒为单位，如果不是，则以毫秒为单位
	 * @return
	 */
	public boolean isSecTimeUnit() {
		return getLocalCache().isSecTimeUnit();
	}

	/**
	 * 本地缓存的有效期的单位是否是以秒为单位，如果不是，则以毫秒为单位
	 * @param secTimeUnit
	 */
	public void setSecTimeUnit(boolean secTimeUnit) {
		getLocalCache().setSecTimeUnit(secTimeUnit);
	}

	@Override
	public void load(Object... args) {
		Lock tmpLock = lock;
		tmpLock.lock();
		try{
			if(loaded){
				logger.warn(new StringBuilder("<load><")
						.append(super.cache.getClass().getName())
						.append("> service has been loaded.").toString());
				return ;
			}
			
			Set<String> keySet = super.extracters.keySet();
			boolean isHappenedError = false;
			for(String key : keySet){
				try {
					load(key, super.extracters.get(key));
				} catch (CachedException e) {
					logger.error(e.getMessage(), e);
					isHappenedError = true;
				}
			}
			if(!isHappenedError)
				loaded = true;
		}finally{
			tmpLock.unlock();
		}
	}

	@Override
	public void unload(Object... args) throws CachedException {
		Lock tmpLock = lock;
		tmpLock.lock();
		try{
			if(!loaded){
				logger.warn(new StringBuilder("<unload><")
						.append(super.cache.getClass().getName())
						.append("> service haven't been loaded.").toString());
				return ;
			}
			
			Set<String> keySet = super.extracters.keySet();
			boolean isHappenedError = false;
			for(String key : keySet){
				try {
					unload(key);
				} catch (CachedException e) {
					logger.error(e.getMessage(), e);
					isHappenedError = true;
				}
			}
			if(!isHappenedError)
				loaded = false;
		}finally{
			tmpLock.unlock();
		}
	}

	@Override
	protected Object load(String key, ISourceExtracter extracter)
			throws CachedException {
		logger.debug(new StringBuilder("<loading><").append(key).append("> ")
				.append(extracter.getClass()));
		
		final String __key = super.appendPrefixKey(key);
		Object source = extracter.extract();
		// 先判断是否要保存到本地
		if(!super.getCacheable().getCachedConfigure().isAlwaysRetriveFromRemote()){
			getLocalCache().put(__key, source);
			logger.debug(new StringBuilder("<load><").append(__key).append("> put into local cache."));
		}
		super.cache.getCachedProxy().put(__key, source, super.getCacheable().getCachedConfigure().getExpiredTime());
		
		logger.debug(new StringBuilder("<load><").append(__key).append("> end load."));
		return source;
	}

	@Override
	protected void unload(String key) throws CachedException {
		logger.debug(new StringBuilder("<unloading><").append(key).append("> "));
		
		final String __key = super.appendPrefixKey(key);
		// 先判断本地是否需要删除
		if(!super.getCacheable().getCachedConfigure().isAlwaysRetriveFromRemote()){
			getLocalCache().remove(__key);
			logger.debug(new StringBuilder("<unload><").append(__key).append("> was remove from local cache."));
		}
		try{
			super.cache.getCachedProxy().remove(__key);
		}catch(NonExistsKeyException e){
			logger.warn(e.getMessage(), e);
		}
		logger.debug(new StringBuilder("<unload><").append(__key).append("> end unload."));
	}
	
	/**
	 * 当获取缓存数据时报出，key不存在时，将会调用{@link #load(String, ISourceExtracter)}方法重新加载。
	 * @param key
	 * @param e
	 * @return
	 * @throws CachedException
	 */
	private Object handleNonExistsKeyException(String key, NonExistsKeyException e) throws CachedException{
		logger.warn("<cachedTemplate><get cached error:" + e.getMessage() + "> reloading");
		Object source = load(key, extracters.get(key));
		logger.warn("<cachedTemplate><relaod> end.");
		return source;
	}

	@Override
	public <T> T get(String key, Class<T> type, Object... args) throws CachedException {
		try{
			/*logger.debug(new StringBuilder("<get> key:").append(key)
					.append(", type:")
					.append(type.getName()).toString());*/
			if(cache.getCachedConfigure().isAlwaysRetriveFromRemote()){
				try{
					return getFromRemote(key, type);
				}catch(NonExistsKeyException e){
					// 如果为Key不存在，可能缓存数据没有加载或者数据已经过期
					// 如果是这样子情况，则需要重新加载
					return type.cast(handleNonExistsKeyException(key, e));
				}
			}		
			T source = getFromLocal(key, type);
			if(null != source){
				return source;
			}
			
			// 如果本地端没有数据，那么先从缓存服务器获取，然后返回
			try{
				source = getFromRemote(key, type);
			}catch(NonExistsKeyException e){
				// 如果为Key不存在，可能缓存数据没有加载或者数据已经过期
				// 如果是这样子情况，则需要重新加载
				source = type.cast(handleNonExistsKeyException(key, e));
			}
			final String __key = appendPrefixKey(key);	
			if(!getLocalCache().containsKey(__key))
				getLocalCache().put(__key, source);
			return source;
		}catch(CachedException e){
			// 最后一道防线，如果本地和远程缓存都爆出异常，那么将从extracter中获取数据
			try{
				return type.cast(extracters.get(key).extract());
			}catch(Exception e1){
				throw new CachedException(e1);
			}
		}finally{
			/*logger.debug(new StringBuilder("<get> finish!").toString());*/
		}
	}

	@Override
	public <T> T get(String key, Class<T> type) throws CachedException {
		try{
			if(cache.getCachedConfigure().isAlwaysRetriveFromRemote()){
				T source = getFromRemoteMayBeNull(key, type);
				if(source == null){
					source = type.cast(load(key, extracters.get(key)));
				}
				return source;
			}		
			T source = getFromLocal(key, type);
			if(null != source)
				return source;
			
			// 如果本地端没有数据，那么先从缓存服务器获取，然后返回
			source = getFromRemoteMayBeNull(key, type);
			if(source == null){
				source = type.cast(load(key, extracters.get(key)));
			}
			final String __key = appendPrefixKey(key);	
			if(!getLocalCache().containsKey(__key))
				getLocalCache().put(__key, source);
			return source;
		}catch(CachedException e){
			// 最后一道防线，如果本地和远程缓存都爆出异常，那么将从extracter中获取数据
			try{
				return type.cast(extracters.get(key).extract());
			}catch(Exception e1){
				throw new CachedException(e1);
			}
		}finally{
			/*logger.debug(new StringBuilder("<get> finish!").toString());*/
		}
	}

	@Override
	public <T> T getFromLocal(String key, Class<T> type) throws CachedException {
		return getLocalCache().get(appendPrefixKey(key), type);	
	}

	/**
	 * 如果远程的缓存中不存在这个Key，将抛出{@link NonExistsKeyException}异常
	 * 
	 * @throws NonExistsKeyException
	 */
	@Override
	public <T> T getFromRemote(String key, Class<T> type)
			throws CachedException {
		return cache.getCachedProxy().get(appendPrefixKey(key), type);
	}

	@Override
	public <T> T getFromRemoteMayBeNull(String key, Class<T> type) throws CachedException {
		return cache.getCachedProxy().getMaybeNull(appendPrefixKey(key), type);
	}

}
