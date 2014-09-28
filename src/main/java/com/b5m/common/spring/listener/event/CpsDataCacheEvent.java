package com.b5m.common.spring.listener.event;

import org.springframework.context.ApplicationEvent;
/**
 * description 
 *
 * @Company b5m
 * @author echo
 * @since 2013-5-20
 */
public class CpsDataCacheEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 504856402633294736L;
	
	public static final String _CPS_DATA_EVENT_SOURCE = "_CPS_DATA_EVENT_SOURCE";

	public CpsDataCacheEvent(Object source) {
		super(source);
	}
	
}
