package com.b5m.common.spring.listener.event;

import org.springframework.context.ApplicationEvent;
/**
 * 
 * description:<br/>
 * 所有数据加载
 * @Company b5m
 * @author echo
 * @since 2013-5-27
 */
public class AllDataLoadEvent extends ApplicationEvent{
    /**
     * 
     */
    private static final long serialVersionUID = -4339838349010478393L;
    public static final String _ALL_EVENT_SOURCE = "_ALL_EVENT_SOURCE";
    
    public AllDataLoadEvent(Object source){
        super(source);
    }
    
}
