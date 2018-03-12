package com.home.wms.entity;

import java.util.Date;

/**
 * Created by fitz on 2018/3/11.
 */
public class Vendor {
	private Long id;
	private String name;
	private String code;
	private String remark;
	private String avator;
	private String phone;
	private Long level;
	private String skill;
	private Integer status;
	private Integer goodScore;
	private Integer moderateScore;
	private Integer badScore;
	private Long organizationId;
	private Long createdBy;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAvator() {
		return avator;
	}

	public void setAvator(String avator) {
		this.avator = avator;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getGoodScore() {
		return goodScore;
	}

	public void setGoodScore(Integer goodScore) {
		this.goodScore = goodScore;
	}

	public Integer getModerateScore() {
		return moderateScore;
	}

	public void setModerateScore(Integer moderateScore) {
		this.moderateScore = moderateScore;
	}

	public Integer getBadScore() {
		return badScore;
	}

	public void setBadScore(Integer badScore) {
		this.badScore = badScore;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
}
