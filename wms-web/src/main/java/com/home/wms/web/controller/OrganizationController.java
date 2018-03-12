package com.home.wms.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.QueryOrganizationParams;
import com.home.wms.dto.QueryRoleParams;
import com.home.wms.entity.Organization;
import com.home.wms.entity.Role;
import com.home.wms.service.OrganizationService;
import com.home.wms.service.RoleService;
import com.ktanx.common.model.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by fitz on 2018/2/26.
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController {
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private RoleService roleService;
	private static final Logger LOG = LoggerFactory.getLogger(OrganizationController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		return "/organization/list";
	}

	@RequestMapping("loadData")
	@ResponseBody
	public JSONObject loadData(QueryOrganizationParams params, Model model){
		JSONObject json = new JSONObject();
		PageList<Organization> pageList = organizationService.findPageOrganizations(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String add(Model model){
		QueryRoleParams params = new QueryRoleParams();
		params.setStatus(1);
		List<Role> roles = roleService.findRoles(params);
		model.addAttribute("roles",roles);
		return "/organization/add";
	}

	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(Organization organization, Model model){
		JSONObject result = new JSONObject();
		try {
			organizationService.saveOrganization(organization);
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
		QueryRoleParams params = new QueryRoleParams();
		params.setStatus(1);
		List<Role> roles = roleService.findRoles(params);
		model.addAttribute("roles",roles);
		model.addAttribute("organization", organizationService.getById(id));
		return "/organization/edit";
	}

	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(Organization organization, Model model){
		JSONObject result = new JSONObject();
		try {
			organizationService.updateOrganization(organization);
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
	public JSONObject delete(@PathVariable Long id, Model model){
		JSONObject result = new JSONObject();
		try {
			organizationService.deleteOrganization(id);
			result.put("code", 0);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}
}
