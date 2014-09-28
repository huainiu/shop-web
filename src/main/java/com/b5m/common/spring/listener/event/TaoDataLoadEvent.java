package com.b5m.common.spring.listener.event;

import org.springframework.context.ApplicationEvent;

/**
 * description
 * 
 * @Company b5m
 * @author echo
 * @since 2013-5-27
 */
public class TaoDataLoadEvent extends ApplicationEvent
{
    /**
     * 
     */
    private static final long serialVersionUID = 2330816200310330557L;
    public static final String _TAO_EVENT_SOURCE = "_TAO_EVENT_SOURCE";

    public TaoDataLoadEvent(Object source, boolean isInit){
        super(source);
        this.isInit = isInit;
    }

    private boolean isInit;

    public boolean isInit(){
        return isInit;
    }
    
}
