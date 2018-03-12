package com.home.wms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.home.wms.dto.PermissionTree;
import com.home.wms.dto.PermissionVo;
import com.home.wms.dto.QueryPermissionParams;
import com.home.wms.entity.Permission;
import com.home.wms.service.PermissionService;
import com.ktanx.common.model.PageList;
import com.ktanx.jdbc.command.entity.Select;
import com.ktanx.jdbc.persist.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.WebEndpoint;
import java.util.List;
import java.util.Map;

/**
 * Created by fitz on 2018/2/23.
 */
@Service
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private JdbcDao jdbcDao;

	@Override
	public PageList<PermissionVo> findPagePermissions(QueryPermissionParams params) {
		StringBuffer sql =  new StringBuffer("select a.*, (select b.name from permission b where b.id = a.pid) pname from permission a where 1");
		List<Object> paramList = Lists.newArrayList();
		if (StrUtil.isNotBlank(params.getName())) {
			sql.append(" and a.name like ?");
			paramList.add("%" + params.getName().trim() + "%");
		}
		if (params.getMenuFlag() != null) {
			sql.append(" and a.menu_flag = ?");
			paramList.add(params.getMenuFlag());
		}
		if (params.getStatus() != null) {
			sql.append(" and a.status = ?");
			paramList.add(params.getStatus());
		}
		if (StrUtil.isNotBlank(params.getPname())) {
			List<Long> ids = jdbcDao.createSelect(Permission.class).include("id").where("status",1).and("name","like","%" + params.getPname().trim() + "%").singleColumnList(Long.class);
			if (ids.isEmpty()) {
				ids.add(-1L);
			}
			sql.append(" and a.pid in(" + StrUtil.join(",",ids) + ")");//in, 就你特殊，注意不能用?和参数的模式。
		}
		sql.append(" order by a.seq asc");
		return (PageList<PermissionVo>)jdbcDao.createNativeExecutor().resultClass(PermissionVo.class).command(sql.toString()).parameters(paramList.toArray()).pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
	}

	@Override
	public List<Permission> findPermissions(QueryPermissionParams params) {
		Permission p = new Permission();
		p.setMenuFlag(params.getMenuFlag());
		p.setStatus(params.getStatus());
		p.setName(params.getName());
		p.setPid(params.getPid());
		Select<Permission> select = jdbcDao.createSelect(Permission.class);
		select.andConditionEntity(p).orderBy("seq").asc();
		return select.list();
	}

	@Override
	public void save(Permission permission) {
		Integer maxSeq = (Integer)jdbcDao.createSelect(Permission.class).addSelectField("max(seq)").notSelectEntityField().objectResult();
		permission.setSeq(maxSeq+1);
		jdbcDao.insert(permission);
	}

	@Override
	public void delete(long id) {
		jdbcDao.delete(Permission.class, id);
	}

	@Override
	public void update(Permission permission) {
		jdbcDao.update(permission);
	}

	@Override
	public Permission getById(long id) {
		return jdbcDao.get(Permission.class,id);
	}
	@Override
	public List<PermissionTree> buildPermissionTree(List<Permission> permissions) {
		List<PermissionTree> permissionTrees =Lists.newArrayList();
		Map<Long,PermissionTree> map = Maps.newHashMap();
		for (Permission permission : permissions) {
			if (permission.getPid() == 0) {
				PermissionTree permissionTree = new PermissionTree();
				permissionTree.setTreeNode(permission);
				map.put(permission.getId(), permissionTree);
				permissionTrees.add(permissionTree);
			} else {
				map.get(permission.getPid()).addSubNode(permission);
			}
		}
		return permissionTrees;
	}
	@Override
	public List<Permission> findPermissionsByIds(List<Long> ids) {
		return jdbcDao.createSelect(Permission.class).where("status",1).and("id", ids.toArray()).list();
	}
}
