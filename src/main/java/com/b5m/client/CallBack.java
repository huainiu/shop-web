package com.b5m.client;

public interface CallBack<T> {
	
	T call(Object ... args);
	
}
