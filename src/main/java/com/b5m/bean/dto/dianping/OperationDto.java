package com.b5m.bean.dto.dianping;

public class OperationDto {

	public boolean oper;
	public String message;
	public Object data;

	public OperationDto() {
		super();
	}

	public OperationDto(boolean oper, String message) {
		super();
		this.oper = oper;
		this.message = message;
	}

	public OperationDto(boolean oper, String message, Object data) {
		super();
		this.oper = oper;
		this.message = message;
		this.data = data;
	}

	public boolean isOper() {
		return oper;
	}

	public void setOper(boolean oper) {
		this.oper = oper;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
