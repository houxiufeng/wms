package com.home.wms.dto;

import java.io.Serializable;

/**
 * Created by fitz on 2018/3/30.
 */
public class OrderTimeSetting implements Serializable{
	private Integer assignWarn;
	private Integer assignOver;
	private Integer checkWarn;
	private Integer checkOver;
	private Integer fixWarn;
	private Integer fixOver;

	public Integer getAssignWarn() {
		return assignWarn;
	}

	public void setAssignWarn(Integer assignWarn) {
		this.assignWarn = assignWarn;
	}

	public Integer getAssignOver() {
		return assignOver;
	}

	public void setAssignOver(Integer assignOver) {
		this.assignOver = assignOver;
	}

	public Integer getCheckWarn() {
		return checkWarn;
	}

	public void setCheckWarn(Integer checkWarn) {
		this.checkWarn = checkWarn;
	}

	public Integer getCheckOver() {
		return checkOver;
	}

	public void setCheckOver(Integer checkOver) {
		this.checkOver = checkOver;
	}

	public Integer getFixWarn() {
		return fixWarn;
	}

	public void setFixWarn(Integer fixWarn) {
		this.fixWarn = fixWarn;
	}

	public Integer getFixOver() {
		return fixOver;
	}

	public void setFixOver(Integer fixOver) {
		this.fixOver = fixOver;
	}
}
