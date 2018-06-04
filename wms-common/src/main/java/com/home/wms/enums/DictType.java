package com.home.wms.enums;

/**
 * Created by fitz on 2018/3/10.
 */
public enum DictType {
	CUSTOMER_LEVEL((short)1,"Customer degree"),
	CUSTOMER_CREDIT((short)2,"Customer credit"),
	PROBLEM_TYPE((short)3,"Problem type"),
	ENGINEER_LEVEL((short)4,"Engineer degree"),
	MAINTAIN_SKILL((short)5,"Maintain capability"),
	PRODUCT_BRAND((short)6,"Product band"),
	SPARE_PART_LIST((short)7,"Spare-part list"),
	CHECK_LIST((short)8,"Check list");

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
