package com.home.wms.dto;

import com.home.wms.entity.User;

/**
 * Created by fitz on 2018/2/24.
 */
public class UserVo extends User{
	private String roleName;
	private String roleCode;
	private String organizationName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
}
