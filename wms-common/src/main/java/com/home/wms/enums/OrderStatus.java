package com.home.wms.enums;

/**
 * Created by fitz on 2018/3/13.
 */
public enum OrderStatus {
	ASSIGNING(0,"派单中"),
	CHECKING(1,"检查中"),
	FIXING(2,"维修中"),
	AUDITING(3,"审核中"),
	COMPLETE(4,"已完结"),
	CANCEL(5,"已取消");

	OrderStatus(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	private int value;
	private String desc;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static String toDesc(int value) {
		for (OrderStatus os : OrderStatus.values()) {
			if (os.getValue() == value) {
				return os.getDesc();
			}
		}
		return "";
	}
}
