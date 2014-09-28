package com.b5m.service.hbase.bean;

public enum CommentType {

	BAD("bad"), GOOD("good"), NORMAL("normal"), ALL("all");

	private String s;

	private CommentType(String s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return s;
	}
	
}
