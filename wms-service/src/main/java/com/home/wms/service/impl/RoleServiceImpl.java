package com.home.wms.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import com.home.wms.dto.QueryRoleParams;
import com.home.wms.entity.Organization;
import com.home.wms.entity.Role;
import com.home.wms.entity.RolePermission;
import com.home.wms.service.RoleService;
import com.home.wms.utils.AppContextManager;
import com.home.wms.utils.MyUtils;
import com.ktanx.common.model.PageList;
import com.ktanx.jdbc.command.entity.Select;
import com.ktanx.jdbc.persist.JdbcDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fitz on 2018/2/23.
 */
@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private JdbcDao jdbcDao;

	@Override
	public PageList<Role> findPageRoles(QueryRoleParams params) {
		Select<Role> s = jdbcDao.createSelect(Role.class);
		if (StringUtils.isNotBlank(params.getCode())) {
			s.and("code",params.getCode().trim());
		}
		if (StringUtils.isNotBlank(params.getName())) {
			s.and("name",params.getName());
		}
		if (params.getStatus() != null) {
			s.and("status",params.getStatus());
		}
		return s.pageList(params.getiDisplayStart()/params.getiDisplayLength()+1, params.getiDisplayLength());
	}

	@Override
	public List<Role> findRoles(QueryRoleParams params) {
		params.setiDisplayStart(0);
		params.setiDisplayLength(Integer.MAX_VALUE);
		return findPageRoles(params);
	}

	@Override
	public void save(Role role) {
        jdbcDao.insert(role);
	}

	@Override
	public void delete(long id) {
        jdbcDao.delete(Role.class, id);
	}

	@Override
	public void update(Role role) {
        jdbcDao.update(role);
	}

	@Override
	public Role getById(long id) {
		return jdbcDao.get(Role.class, id);
	}
	@Override
	public List<RolePermission> findRolePermissionByRoleId(Long roleId) {
		RolePermission rp = new RolePermission();
		rp.setRoleId(roleId);
		return jdbcDao.queryList(rp);
	}
	@Override
	@Transactional
	public void editRolePermissions(Long roleId, Long[] permissionIds) {
		deleteRolePermissionByRoleId(roleId);
		for (Long permissionId : permissionIds) {
			RolePermission rp = new RolePermission();
			rp.setRoleId(roleId);
			rp.setPermissionId(permissionId);
			jdbcDao.insert(rp);
		}
	}

	@Override
	public void deleteRolePermissionByRoleId(Long roleId) {
		RolePermission rolePermission = new RolePermission();
		rolePermission.setRoleId(roleId);
		jdbcDao.delete(rolePermission);
	}

	@Override
	public List<Role> findRolesByIds(List<Long> ids) {
		Select<Role> s = jdbcDao.createSelect(Role.class);
		return s.where("id","in", ids.toArray()).list();
	}

	@Override
	public List<Role> findOrganizationRoles() {
		QueryRoleParams params = new QueryRoleParams();
		params.setStatus(1);
		if(AppContextManager.getCurrentUserInfo().getRoleCode().equals("admin")) {
			return findRoles(params);
		} else {
			Long organizationId = AppContextManager.getCurrentUserInfo().getOrganizationId();
			Organization organization = jdbcDao.get(Organization.class, organizationId);
			List<Long> roleIds = MyUtils.strToLongList(organization.getRoleIds());
			return findRolesByIds(roleIds);

		}
	}
}
