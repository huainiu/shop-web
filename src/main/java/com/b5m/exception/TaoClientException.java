package com.b5m.exception;

public class TaoClientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5002652887725327875L;

	public TaoClientException(Exception e){
		super(e);
	}
	
	public TaoClientException(String msg){
		super(msg);
	}
}
