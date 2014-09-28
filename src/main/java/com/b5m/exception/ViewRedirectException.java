package com.b5m.exception;

public class ViewRedirectException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8678306896567461534L;

	private final String view;
	
	public ViewRedirectException(String reason, String view){
		super(reason);
		this.view = view;
	}

	public String getView() {
		return view;
	}
	
}
