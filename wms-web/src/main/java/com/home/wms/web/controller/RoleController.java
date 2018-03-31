package com.home.wms.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.PermissionVo;
import com.home.wms.dto.QueryPermissionParams;
import com.home.wms.dto.QueryRoleParams;
import com.home.wms.entity.Permission;
import com.home.wms.entity.Role;
import com.home.wms.entity.RolePermission;
import com.home.wms.service.PermissionService;
import com.home.wms.service.RoleService;
import com.ktanx.common.model.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by fitz on 2018/2/23.
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		return "/role/list";
	}

	@RequestMapping("loadData")
	@ResponseBody
	public JSONObject loadData(QueryRoleParams params, Model model){
		JSONObject json = new JSONObject();
		PageList<Role> pageList = roleService.findPageRoles(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String add(Model model){
		return "/role/add";
	}

	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(Role role, Model model){
		JSONObject result = new JSONObject();
		try {
			roleService.save(role);
			result.put("code", 0);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Long id, Model model){
		model.addAttribute("role", roleService.getById(id));
		return "/role/edit";
	}

	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(Role role, Model model){
		JSONObject result = new JSONObject();
		try {
			roleService.update(role);
			result.put("code", 0);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject delete(@PathVariable Integer id, Model model){
		JSONObject result = new JSONObject();
		try {
			roleService.delete(id);
			result.put("code", 0);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="/editPermission/{id}", method = RequestMethod.GET)
	public String editResource(@PathVariable Long id, Model model){
		model.addAttribute("roleId", id);
		QueryPermissionParams params = new QueryPermissionParams();
		params.setStatus(1);
		List<Permission> permissions = permissionService.findPermissions(params);
		model.addAttribute("permissionTrees", permissionService.buildPermissionTree(permissions));
		List<RolePermission> rolePermissionList = roleService.findRolePermissionByRoleId(id);
		if (rolePermissionList != null && rolePermissionList.size() > 0) {
			model.addAttribute("rolePermissions", JSON.toJSONString(rolePermissionList));
		}
		Role role = roleService.getById(id);
		model.addAttribute("roleName", role.getName());
		return "/role/edit_permission";
	}

	@RequestMapping(value="/editRolePermissions", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject editRoleResources(@RequestParam("roleId")Long roleId, @RequestParam("permissionIds[]") Long[] permissionIds){
		JSONObject result = new JSONObject();
		try {
			roleService.editRolePermissions(roleId, permissionIds);
			result.put("code", 0);
		} catch(Exception e) {
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", "edit error");
		}
		return result;
	}
}
