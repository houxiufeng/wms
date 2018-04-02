package com.home.wms.web.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.PermissionVo;
import com.home.wms.dto.QueryPermissionParams;
import com.home.wms.entity.Permission;
import com.home.wms.service.PermissionService;
import com.home.wms.service.RoleService;
import com.ktanx.common.model.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    
    private static final Logger LOG = LoggerFactory.getLogger(PermissionController.class);
    
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model){
		return "/permission/list";
    }

    @RequestMapping("loadData")
    @ResponseBody
    public JSONObject loadData(QueryPermissionParams params, Model model){
    	JSONObject json = new JSONObject();
    	PageList<PermissionVo> pageList = permissionService.findPagePermissions(params);
    	json.put("aaData", pageList);
    	json.put("iTotalRecords", pageList.getPager().getTotalItems());
    	json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
    	return json;
    } 
    
    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String add(Model model){
	    QueryPermissionParams params = new QueryPermissionParams();
	    params.setMenuFlag(1);
	    params.setStatus(1);
	    params.setPid(0L);
	    model.addAttribute("permissions", permissionService.findPermissions(params));
        return "/permission/add";
    } 
    
    @RequestMapping(value="/create", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject create(Permission permission, Model model){
    	JSONObject result = new JSONObject();
    	try {
    		permissionService.save(permission);
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
        model.addAttribute("permission", permissionService.getById(id));
	    QueryPermissionParams params = new QueryPermissionParams();
	    params.setMenuFlag(1);
	    params.setStatus(1);
	    params.setPid(0L);
	    model.addAttribute("permissions", permissionService.findPermissions(params));
        return "/permission/edit";
    } 
    
    @RequestMapping(value="/update", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(Permission permission, Model model){
    	JSONObject result = new JSONObject();
    	try {
    		permissionService.update(permission);
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
		    if(roleService.findRolePermissionByPermissionId(id).size() > 0) {
			    result.put("code", 1);
			    result.put("message", "can't delete, already in used!");
			    return result;
		    }
    		permissionService.delete(id);
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
