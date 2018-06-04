package com.home.wms.dto;

import com.home.wms.entity.Branch;

/**
 * Created by fitz on 2018/3/13.
 */
public class BranchVo extends Branch {
	private String customerName;
	private String userName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
