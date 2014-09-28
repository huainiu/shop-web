package com.b5m.cached;

import java.io.Serializable;
import java.util.Properties;

/**
 * <p>可缓存的实例的相关配置，例如设置缓存过期时间，缓存的Key的前缀等等。
 * 
 * @author Jacky LIU
 *
 */
public class CachedConfigure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -323245069768536115L;

	/**
	 * 此属性对象，存放了额外的一些配置，这些配置，根据相关的Proxy或者ICacheable子类调用。
	 */
	private final Properties props;
	
	/**
	 * 缓存数据的有效期。
	 */
	private long expiredTime = 60 * 60 * 24;	// one day
	
	/**
	 * 可缓存对象域中的索引前缀。
	 */
	private String keyPrefix;
	
	/**
	 * cas原子操作的重试次数。
	 */
	private int casRetryCount;
	
	/**
	 * 如果可缓存对象的数据比较小，应用端如果想减少访问memcached的带宽成本，
	 * 可以考虑将此数据缓存到应用端的内存中。但是需要在应用端维持数据的有效期。
	 * 如果这个值，设置为true，那么应用端每次访问，都会从memcached中获取
	 */
	private boolean alwaysRetriveFromRemote = true;
	
	/**
	 * 
	 * @param props 此属性对象，存放了额外的一些配置，这些配置，根据相关的Proxy或者ICacheable子类调用。
	 */
	public CachedConfigure(Properties props){
		this.props = props;
	}
	
	public CachedConfigure(){
		this(new Properties());
	}

	/**
	 * 缓存数据的有效期。
	 */
	public long getExpiredTime() {
		return expiredTime;
	}

	/**
	 * 缓存数据的有效期。
	 */
	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}
	
	/**
	 * 可缓存对象域中的索引前缀。
	 */
	public String getKeyPrefix() {
		return keyPrefix;
	}

	/**
	 * 可缓存对象域中的索引前缀。
	 */
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	/**
	 * cas原子操作的重试次数。
	 */
	public int getCasRetryCount() {
		return casRetryCount;
	}

	/**
	 * cas原子操作的重试次数。
	 */
	public void setCasRetryCount(int casRetryCount) {
		this.casRetryCount = casRetryCount;
	}
	
	/**
	 * 此属性对象，存放了额外的一些配置，这些配置，根据相关的Proxy或者ICacheable子类调用。
	 */
	public Properties getProps() {
		return props;
	}

	/**
	 * 如果可缓存对象的数据比较小，应用端如果想减少访问memcached的带宽成本，
	 * 可以考虑将此数据缓存到应用端的内存中。但是需要在应用端维持数据的有效期。
	 * 如果这个值，设置为true，那么应用端每次访问，都会从memcached中获取
	 */
	public boolean isAlwaysRetriveFromRemote() {
		return alwaysRetriveFromRemote;
	}

	/**
	 * 如果可缓存对象的数据比较小，应用端如果想减少访问memcached的带宽成本，
	 * 可以考虑将此数据缓存到应用端的内存中。但是需要在应用端维持数据的有效期。
	 * 如果这个值，设置为true，那么应用端每次访问，都会从memcached中获取
	 */
	public void setAlwaysRetriveFromRemote(boolean alwaysRetriveFromRemote) {
		this.alwaysRetriveFromRemote = alwaysRetriveFromRemote;
	}
	
	
}
