package com.home.wms.enums;

/**
 * Created by fitz on 2018/3/13.
 */
public enum RoleCode {
	ADMIN("admin"),
	BOSS("boss"),
	CUSTOMER("customer"),
	ENGINEER("engineer"),
	REGISTER("register"),
	USER("user");

	RoleCode(String code) {
		this.code = code;
	}

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
