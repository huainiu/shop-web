package com.b5m.exception;
/**
 * description 
 *
 * @Company b5m
 * @author echo
 * @since 2013-5-28
 */
public class TaoFetchDataException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = -3837096892077460987L;
    
    public TaoFetchDataException(String message){
        super(message);
    }
    
    public TaoFetchDataException(String message, Throwable e){
        super(message, e);
    }

}
