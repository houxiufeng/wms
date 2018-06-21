package com.home.wms.dto;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.home.wms.entity.Torder;
import com.home.wms.enums.OrderStatus;

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
	private String engineerName;
	private Integer warnHours;
	private Integer overHours;
	private String branchCity;

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

	public String getEngineerName() {
		return engineerName;
	}

	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
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

	public String getBranchCity() {
		return branchCity;
	}

	public void setBranchCity(String branchCity) {
		this.branchCity = branchCity;
	}

	public Boolean getWarned() {
		if (this.warnHours != null && this.warnHours != 0) {
			boolean b = false;
			if (this.getStatus() == OrderStatus.ASSIGNING.getValue()) {
				b = DateUtil.offset(this.getCreatedTime(), DateField.HOUR, this.warnHours).before(new Date());
			} else if (this.getStatus() == OrderStatus.CHECKING.getValue()) {
				b = DateUtil.offset(this.getCheckTime(), DateField.HOUR, this.warnHours).before(new Date());
			} else if (this.getStatus() == OrderStatus.FIXING.getValue()) {
				b = DateUtil.offset(this.getFixTime(), DateField.HOUR, this.warnHours).before(new Date());
			}
			return b;
		} else {
			return false;
		}
	}

	public Boolean getOvered() {
		if (this.overHours != null && this.overHours != 0) {
			boolean b = false;
			if (this.getStatus() == OrderStatus.ASSIGNING.getValue()) {
				b = DateUtil.offset(this.getCreatedTime(), DateField.HOUR, this.overHours).before(new Date());
			} else if (this.getStatus() == OrderStatus.CHECKING.getValue()) {
				b = DateUtil.offset(this.getCheckTime(), DateField.HOUR, this.overHours).before(new Date());
			} else if (this.getStatus() == OrderStatus.FIXING.getValue()) {
				b = DateUtil.offset(this.getFixTime(), DateField.HOUR, this.overHours).before(new Date());
			}
			return b;
		} else {
			return false;
		}
	}

}
