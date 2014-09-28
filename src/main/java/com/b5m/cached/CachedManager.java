package com.b5m.cached;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.b5m.cached.exception.CachedException;

/**
 * <p>缓存管理，管理所有注册的可缓存的对象，并控制他们的生命周期，例如加载，销毁等等。
 * @author Jacky Liu
 *
 */
public class CachedManager {

	private List<ICacheable> cachedObjects;
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private final Lock lock = new ReentrantLock();
	
	private ICacheLoadErrorHandler cacheLoadErrorHandler;
	
	public CachedManager(){
		
	}

	public List<ICacheable> getCachedObjects() {
		if(null == cachedObjects)
			cachedObjects = new ArrayList<ICacheable>();
		return cachedObjects;
	}

	public void setCachedObjects(List<ICacheable> cachedObjects) {
		this.cachedObjects = cachedObjects;
	}
	
	public void load(){
		//this.load(new Object[0]);
	}
	
	public void load(Object... args){
		Lock tmpLock = lock;
		tmpLock.lock();
		try{
			for(ICacheable cached : cachedObjects){
				long startTime = System.currentTimeMillis();
				try {
					String cacheTypeName = cached.getClass().getName();
					logger.info("loading->" + cacheTypeName);
					boolean flag = cached.load(args);
					long loadedTime = System.currentTimeMillis() - startTime;
					if(flag){
						logger.info(new StringBuilder("loaded->").append(cacheTypeName)
								.append(", took ").append(loadedTime)
								.append(" ms."));						
					}
				} catch (CachedException e) {
					logger.error(e.getMessage(), e);
					getCacheLoadErrorHandler().handleErrorLoading(cached, args);
				}
			}
		}finally{
			tmpLock.unlock();
		}
	}
	
	public void unload(){
		Lock tmpLock = lock;
		tmpLock.lock();
		try{
			for(ICacheable cached : cachedObjects){
				long startTime = System.currentTimeMillis();
				try {
					String cacheTypeName = cached.getClass().getName();
					logger.info("unloading->" + cacheTypeName);
					boolean flag = cached.unload();
					long loadedTime = System.currentTimeMillis() - startTime;
					if(flag){
						logger.info(new StringBuilder("unloaded->").append(cacheTypeName)
								.append(", took ").append(loadedTime)
								.append(" ms."));						
					}
				} catch (CachedException e) {
					logger.error(e.getMessage(), e);
					getCacheLoadErrorHandler().handleErrorLoading(cached);
				}
			}
			
			// 关闭缓存代理客户端
			for(ICacheable cached : cachedObjects){
				if(cached.getCachedProxy().isStart()){
					logger.debug("<closing><cached client> " + cached.getCachedProxy().getClientName());
					try {
						cached.getCachedProxy().stop();
						logger.debug("<closed><cached client> " + cached.getCachedProxy().getClientName());
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}finally{
			tmpLock.unlock();
		}
	}
	
	public ICacheLoadErrorHandler getCacheLoadErrorHandler() {
		if(null == cacheLoadErrorHandler)
			cacheLoadErrorHandler = new AbortCacheLoadError();
		return cacheLoadErrorHandler;
	}

	public void setCacheLoadErrorHandler(
			ICacheLoadErrorHandler cacheLoadErrorHandler) {
		this.cacheLoadErrorHandler = cacheLoadErrorHandler;
	}

	public static class AbortCacheLoadError implements ICacheLoadErrorHandler{

		@Override
		public void handleErrorLoading(ICacheable cacheable, Object... args) {
			// abort handling.
			// just leave blank. do nothing.
		}
		
	}
}
