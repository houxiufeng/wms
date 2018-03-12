package com.home.wms.dto;

import com.home.wms.entity.Permission;

/**
 * Created by fitz on 2018/2/23.
 */
public class PermissionVo extends Permission{
	private String pname;
	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
}
