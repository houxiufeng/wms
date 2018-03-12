package com.home.wms.dto;

/**
 * Created by fitz on 2018/2/26.
 */
public class QueryBranchProductParams {
	private Long organizationId;
	private Integer iDisplayStart = 0;
	private Integer iDisplayLength = 10;

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
}
