package com.home.wms.web.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.QueryUserParams;
import com.home.wms.dto.UserVo;
import com.home.wms.entity.Role;
import com.home.wms.entity.User;
import com.home.wms.enums.RoleCode;
import com.home.wms.service.AsyncService;
import com.home.wms.service.OrganizationService;
import com.home.wms.service.RoleService;
import com.home.wms.service.UserService;
import com.ktanx.common.model.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private AsyncService asyncService;
    
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model){
	    model.addAttribute("organizations", organizationService.findAll());
		return "/user/list";
    }

    @RequestMapping("loadData")
    @ResponseBody
    public JSONObject loadData(QueryUserParams params, Model model){
    	JSONObject json = new JSONObject();
    	PageList<UserVo> pageList = userService.findPageUsers(params);
    	json.put("aaData", pageList);
    	json.put("iTotalRecords", pageList.getPager().getTotalItems());
    	json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
    	return json;
    } 
    
    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String add(Model model){
	    model.addAttribute("roles",roleService.findOrganizationRoles());
	    model.addAttribute("organizations",organizationService.findAll());
        return "/user/add";
    } 
    
    @RequestMapping(value="/create", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject create(User user, Model model){
    	JSONObject result = new JSONObject();
    	String password =  user.getPassword();
    	try {
    		user.setPassword(SecureUtil.md5(password));
    		userService.save(user);
    		result.put("code", 0);
		    asyncService.sendMail(user.getEmail(),"Welcome " + user.getName(),StrUtil.format(" Username:{}\n Password:{}\nPlease login the system for change the password", user.getEmail(), password));
//    		Role role = roleService.getById(user.getRoleId());
//    		if (role != null && role.getCode().equals(RoleCode.CUSTOMER.getCode())) {//如果是customer用户，需要发邮件.
//			    asyncService.sendMail("houxiufeng@edianzu.cn","Account info",StrUtil.format("your account is:{}, password is:{}", user.getEmail(), password));
//		    }
    	} catch(DuplicateKeyException dkException) {
    		result.put("code", 1);
    		result.put("message", StrUtil.format("duplicate user name:{}", user.getName()));
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
	    model.addAttribute("roles",roleService.findOrganizationRoles());
	    model.addAttribute("organizations",organizationService.findAll());
	    model.addAttribute("user", userService.getById(id));
        return "/user/edit";
    } 
    
    @RequestMapping(value="/update", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(User user, Model model){
    	JSONObject result = new JSONObject();
    	try {
    		userService.update(user);
    		result.put("code", 0);
    	} catch(DuplicateKeyException dkException) {
    		result.put("code", 1);
    		result.put("message", StrUtil.format("duplicate user name:{}", user.getName()));
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
    		userService.delete(id);
    		result.put("code", 0);
    	} catch(Exception e) {
    		e.printStackTrace();
    		LOG.error(e.getMessage());
    		result.put("code", 1);
    		result.put("message", e.getMessage());
    	}
        return result;
    }

	@RequestMapping(value="/resetPwd", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject resetPwd(@RequestParam Long id, @RequestParam String password, Model model){
		JSONObject result = new JSONObject();
		try {
			User user = new User();
			user.setPassword(password);
			user.setId(id);
			userService.update(user);
			result.put("code", 0);
		} catch(Exception e) {
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}
}
