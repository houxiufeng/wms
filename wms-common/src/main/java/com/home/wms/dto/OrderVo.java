package com.home.wms.dto;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.home.wms.entity.Torder;

import java.util.Date;

/**
 * Created by fitz on 2018/3/13.
 */
public class OrderVo extends Torder{
	private String customerName;
	private String branchName;
	private String productName;
	private String productModel;
	private String typeName;
	private String vendorName;
	private Integer warnHours;
	private Integer overHours;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer getWarnHours() {
		return warnHours;
	}

	public void setWarnHours(Integer warnHours) {
		this.warnHours = warnHours;
	}

	public Integer getOverHours() {
		return overHours;
	}

	public void setOverHours(Integer overHours) {
		this.overHours = overHours;
	}

	public Boolean getWarned() {
		if (this.warnHours != null && this.warnHours != 0) {
			return DateUtil.offset(this.getCreatedTime(), DateField.HOUR, this.warnHours).before(new Date());
		} else {
			return false;
		}
	}

	public Boolean getOvered() {
		if (this.overHours != null && this.overHours != 0) {
			return DateUtil.offset(this.getCreatedTime(), DateField.HOUR, this.overHours).before(new Date());
		} else {
			return false;
		}
	}

}
