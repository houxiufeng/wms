package com.home.wms.service;

import com.home.wms.dto.QueryRoleParams;
import com.home.wms.entity.Role;
import com.home.wms.entity.RolePermission;
import com.ktanx.common.model.PageList;

import java.util.List;

/**
 * Created by fitz on 2018/2/23.
 */
public interface RoleService {
	PageList<Role> findPageRoles(QueryRoleParams params);

	List<Role> findRoles(QueryRoleParams params);

	void save(Role role);

	void delete(long id);

	void update(Role role);

	Role getById(long id);

	List<RolePermission> findRolePermissionByRoleId(Long roleId);

	List<RolePermission> findRolePermissionByPermissionId(Long permissionId);

	void editRolePermissions(Long roleId, Long[] permissionIds);

	void deleteRolePermissionByRoleId(Long roleId);

	List<Role> findRolesByIds(List<Long> ids);

	List<Role> findOrganizationRoles();
}
