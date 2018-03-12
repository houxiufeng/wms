package com.home.wms.service;

import com.home.wms.dto.PermissionTree;
import com.home.wms.dto.PermissionVo;
import com.home.wms.dto.QueryPermissionParams;
import com.home.wms.entity.Permission;
import com.ktanx.common.model.PageList;

import java.util.List;

/**
 * Created by fitz on 2018/2/23.
 */
public interface PermissionService {

	PageList<PermissionVo> findPagePermissions(QueryPermissionParams params);

	List<Permission> findPermissions(QueryPermissionParams params);

	void save(Permission permission);

	void delete(long id);

	void update(Permission permission);

	Permission getById(long id);

	List<PermissionTree> buildPermissionTree(List<Permission> permissions);

	List<Permission> findPermissionsByIds(List<Long> ids);



}
