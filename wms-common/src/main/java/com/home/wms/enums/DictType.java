package com.home.wms.enums;

/**
 * Created by fitz on 2018/3/10.
 */
public enum DictType {
	CUSTOMER_LEVEL((short)1,"客户级别"),
	CUSTOMER_CREDIT((short)2,"客户信用"),
	CONFIRM_PROBLEM((short)3,"确认问题"),
	PROBLEM_TYPE((short)4,"问题类型"),
	VENDOR_LEVEL((short)5,"供应商级别"),
	MAINTAIN_SKILL((short)6,"产品维修能力"),
	PRODUCT_BRAND((short)7,"产品名称");

	DictType(Short value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	private Short value;
	private String desc;

	public Short getValue() {
		return value;
	}

	public void setValue(Short value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static String toDesc(Short value) {
		for (DictType dt : DictType.values()) {
			if (dt.getValue() == value) {
				return dt.getDesc();
			}
		}
		return "";
	}
}
