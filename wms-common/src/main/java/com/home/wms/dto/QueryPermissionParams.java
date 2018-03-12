package com.home.wms.dto;

/**
 * Created by fitz on 2018/2/23.
 */
public class QueryPermissionParams {
	private String name;
	private Integer menuFlag;
	private String pname;
	private Integer status;
	private Long pid;
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

	public Integer getMenuFlag() {
		return menuFlag;
	}

	public void setMenuFlag(Integer menuFlag) {
		this.menuFlag = menuFlag;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
}
