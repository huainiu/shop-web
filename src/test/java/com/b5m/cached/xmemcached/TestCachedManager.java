package com.b5m.cached.xmemcached;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.b5m.cached.CachedBase;
import com.b5m.cached.CachedConfigure;
import com.b5m.cached.CachedManager;
import com.b5m.cached.CachedProxyBase;
import com.b5m.cached.ICacheLoadErrorHandler;
import com.b5m.cached.ICacheable;
import com.b5m.cached.ICachedProxy;
import com.b5m.cached.exception.CachedException;

public class TestCachedManager {

	@Before
	public void setUp(){
		org.apache.log4j.BasicConfigurator.configure();
	}
	
	@Test
	public void testLoad() {
		CachedManager manager = new CachedManager();
		CachedConfigure configure = new CachedConfigure();
		SampleCachedProxy proxy = new SampleCachedProxy();
		SampleCached cached = new SampleCached(proxy, configure);
		SampleCached cached2 = new SampleCached(proxy, configure);
		
		manager.getCachedObjects().add(cached);
		manager.getCachedObjects().add(cached2);
		
		manager.load();
	}
	
	@Test
	public void testErrorHandling(){
		CachedManager manager = new CachedManager();
		CachedConfigure configure = new CachedConfigure();
		SampleCachedProxy proxy = new SampleCachedProxy();
		SampleCached cached = new SampleCached(proxy, configure);
		ErrorCached cached2 = new ErrorCached(proxy, configure);
		
		manager.getCachedObjects().add(cached);
		manager.getCachedObjects().add(cached2);
		manager.setCacheLoadErrorHandler(new ErrorHandler(cached2.getClass().getName()));
		
		manager.load();
	}
	
	public class ErrorHandler implements ICacheLoadErrorHandler{

		private final String throwedCached;
		
		public ErrorHandler(String throwedCached){
			this.throwedCached = throwedCached;
		}
		
		@Override
		public void handleErrorLoading(ICacheable cacheable, Object... args) {
			Assert.assertEquals(this.throwedCached, cacheable.getClass().getName());
		}
		
	}
	
	public class ErrorCached extends CachedBase{
		public ErrorCached(ICachedProxy proxy, CachedConfigure configure) {
			super(proxy, configure);
		}

		@Override
		public boolean load(Object... args) throws CachedException {
			throw new CachedException("Exception for testing");
		}

		@Override
		public boolean unload(Object... args) throws CachedException {
			// TODO Auto-generated method stub
			return false;
		}
	}
	
	public class SampleCached extends CachedBase{

		public SampleCached(ICachedProxy proxy, CachedConfigure configure) {
			super(proxy, configure);
		}

		@Override
		public boolean load(Object... args) throws CachedException {
			return true;
		}

		@Override
		public boolean unload(Object... args) throws CachedException {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	public class SampleCachedProxy extends CachedProxyBase{

		@Override
		public String getClientName() {
			return "Client Name";
		}

		@Override
		public <T> T getProxy(Class<T> type) {
			return null;
		}

		@Override
		public void stop() throws IOException {
			super.start = false;
		}

		@Override
		public long getCounter(String key) throws CachedException {
			return 0;
		}

		@Override
		public long increase(String key, long delta, long initValue)
				throws CachedException {
			return 0;
		}

		@Override
		public long decrease(String key, long delta, long initValue)
				throws CachedException {
			return 0;
		}

		@Override
		public void put(String key, Object value, long expiredTime)
				throws CachedException {
			
		}

		@Override
		public void delete(String key) throws CachedException {
			
		}

		@Override
		protected <T> T get(String key, Class<T> type, long retriveTimeout)
				throws CachedException {
			return null;
		}

		@Override
		protected void add(String key, Object value, long expiredTime,
				long writeTimeout) throws CachedException {
			
		}

		@Override
		public void remove(String key) throws CachedException {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected <T> T getMaybeNull(String key, Class<T> type,
				long retriveTimeout) throws CachedException {
			return null;
		}

		@Override
		public <T> Map<String, T> gets(List<String> keyCollections, Class<T> type, long timeout) throws CachedException {
			return null;
		}

		@Override
		public Map<String, Object> gets(List<String> keyCollections, long timeout) throws CachedException {
			return null;
		}
		
		
	}

}
