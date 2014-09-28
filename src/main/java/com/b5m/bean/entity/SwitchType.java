package com.b5m.bean.entity;

public enum SwitchType {
	clothing("服饰", "clothing"), _3c("3c", "ccc"), cosmetic("化妆品", "cosmetic"), furniture("家居", "furniture");

	private String name;
	private String value;

	SwitchType(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
