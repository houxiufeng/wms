package com.home.wms.enums;

/**
 * Created by fitz on 2018/3/13.
 */
public enum OrderStatus {
	ASSIGNING(0,"Assigning"),
	CHECKING(1,"Checking"),
	FIXING(2,"Fixing"),
	AUDITING(3,"Auditing"),
	COMPLETE(4,"Complete"),
	CANCEL(5,"Cancel");

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
