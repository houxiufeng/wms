package com.home.wms.dto;

/**
 * Created by fitz on 2018/2/26.
 */
public class QueryOrganizationParams {
	private String name;
	private Integer iDisplayStart = 0;
	private Integer iDisplayLength = 10;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
