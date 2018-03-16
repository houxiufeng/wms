package com.home.wms.dto;

import com.home.wms.entity.Customer;

/**
 * Created by fitz on 2018/3/16.
 */
public class CustomerVo extends Customer {
	private String typeName;
	private String creditStatusName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCreditStatusName() {
		return creditStatusName;
	}

	public void setCreditStatusName(String creditStatusName) {
		this.creditStatusName = creditStatusName;
	}
}
