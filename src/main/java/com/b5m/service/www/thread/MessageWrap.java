package com.b5m.service.www.thread;


public class MessageWrap<T> {
	private MessageOp op;
    private T message;
    
    public MessageWrap(MessageOp op,  T message){
        this.op = op;
        this.message = message;
    }

    public static <T> MessageWrap<T> newInstance(MessageOp op, T message){
        return new MessageWrap<T>(op, message);
    }

	public MessageOp getOp() {
		return op;
	}

	public void setOp(MessageOp op) {
		this.op = op;
	}

	public T getMessage() {
		return message;
	}

	public void setMessage(T message) {
		this.message = message;
	}
    
}
