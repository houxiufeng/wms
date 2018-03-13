package com.home.wms.dto;

import com.home.wms.entity.Branch;

/**
 * Created by fitz on 2018/3/13.
 */
public class BranchVo extends Branch {
	private String customerName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
