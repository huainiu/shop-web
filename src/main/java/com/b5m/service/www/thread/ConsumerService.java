package com.b5m.service.www.thread;


public interface ConsumerService<T> {
	
	void addMessage(T t, MessageOp op) throws InterruptedException;
	
}
