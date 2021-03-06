package com.home.wms.dto;

/**
 * Created by fitz on 2018/3/10.
 */
public class QueryDictParams {
	private String name;
	private Short type;
	private Long organizationId;
	private Integer iDisplayStart = 0;
	private Integer iDisplayLength = 10;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(Integer iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public Integer getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(Integer iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
}
