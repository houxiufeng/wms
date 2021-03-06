package com.home.wms.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by fitz on 2018/3/4.
 */
public class Customer {
	private Long id;
	private String name;
	private Long createdBy;
	private Long organizationId;
	private String code;
	private String website;
	private String phone;
	private String legalPerson;
	private String legalPhone;
	private String contactPerson;
	private String contactPhone;
	private Integer type;
	private Integer creditStatus;
	private String contractName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date contractFrom;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date contractTo;
	private String contractPosition;
	private Double contractAmount;
	private String contractInfo;
	private String registNo;
	private String logo;
	private String groups;
	private String remark;
	private Date createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getLegalPhone() {
		return legalPhone;
	}

	public void setLegalPhone(String legalPhone) {
		this.legalPhone = legalPhone;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCreditStatus() {
		return creditStatus;
	}

	public void setCreditStatus(Integer creditStatus) {
		this.creditStatus = creditStatus;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Date getContractFrom() {
		return contractFrom;
	}

	public void setContractFrom(Date contractFrom) {
		this.contractFrom = contractFrom;
	}

	public Date getContractTo() {
		return contractTo;
	}

	public void setContractTo(Date contractTo) {
		this.contractTo = contractTo;
	}

	public String getContractPosition() {
		return contractPosition;
	}

	public void setContractPosition(String contractPosition) {
		this.contractPosition = contractPosition;
	}

	public Double getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getContractInfo() {
		return contractInfo;
	}

	public void setContractInfo(String contractInfo) {
		this.contractInfo = contractInfo;
	}

	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}


	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
