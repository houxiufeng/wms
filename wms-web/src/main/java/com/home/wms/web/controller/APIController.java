package com.home.wms.web.controller;

import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.*;
import com.home.wms.entity.*;
import com.home.wms.service.*;
import com.home.wms.utils.AppConstants;
import com.home.wms.utils.AppContextManager;
import com.home.wms.utils.MyUtils;
import com.ktanx.common.model.PageList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by fitz on 2018/4/16.
 */
@Controller
@RequestMapping("/api")
public class APIController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private BranchProductService branchProductService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private CustomerService customerService;

	private static final Logger LOG = LoggerFactory.getLogger(APIController.class);

	@RequestMapping(value="login", method = RequestMethod.POST)
	@ResponseBody
	public WMSResponse login(@RequestParam String email, @RequestParam String password, HttpSession session) {
		WMSResponse response = new WMSResponse();
		User user = userService.findByEmailAndPwd(email, password);
		if (user != null) {
			Organization organization = null;
			if (user.getOrganizationId() != null) {
				organization = organizationService.getById(user.getOrganizationId());
				if (organization == null || organization.getStatus() == 0) {
					response.setCode(1);
					response.setMessage("Organization does't exist!");
					return response;
				}
			}
			Role role = roleService.getById(user.getRoleId());
			if (role == null || role.getStatus() == 0) {
				response.setCode(1);
				response.setMessage("Role does't exist!");
				return response;
			}

			if (StringUtils.isBlank(user.getToken())) {
				String newToken = MyUtils.generateToken(user.getEmail(), user.getPassword(), user.getOrganizationId());
				user.setToken(newToken);
				userService.update(user);
			} else {//token 不为空，验证token 是否已经改变
				String newToken = MyUtils.generateToken(user.getEmail(), user.getPassword(), user.getOrganizationId());
				if(!StringUtils.equals(newToken, user.getToken())) {
					user.setToken(newToken);
					userService.update(user);
					response.setCode(1);
					response.setMessage("Token invalid, please re-login");
					return response;
				}

			}

			CurrentUserInfo currentUserInfo = new CurrentUserInfo();
			currentUserInfo.setId(user.getId());
			currentUserInfo.setName(user.getName());
			currentUserInfo.setEmail(user.getEmail());
			currentUserInfo.setRoleId(role.getId());
			currentUserInfo.setRoleName(role.getName());
			currentUserInfo.setRoleCode(role.getCode());
			currentUserInfo.setToken(user.getToken());
			if (organization != null) {
				currentUserInfo.setOrganizationId(organization.getId());
				currentUserInfo.setOrganizationName(organization.getName());
			}
			session.setAttribute(AppConstants.CURRENT_USER, currentUserInfo);
			response.setData(currentUserInfo);
		} else {
			response.setCode(1);
			response.setMessage("Wrong Email or Password!");
		}
		return response;
	}

	@RequestMapping(value="/customers")
	@ResponseBody
	public JSONObject customers(@RequestBody QueryCustomerParams params, Model model){
		JSONObject json = new JSONObject();
		try {
			params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
			params.setiDisplayLength(Integer.MAX_VALUE);
			PageList<CustomerVo> list = customerService.findPageCustomers(params);
			json.put("list", list);
			json.put("code", 0);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			json.put("code",1);
			json.put("message", e.getMessage());
		}
		return json;
	}

	@RequestMapping(value="/branchs")
	@ResponseBody
	public JSONObject branchs(@RequestBody QueryBranchParams params, Model model){
		JSONObject json = new JSONObject();
		try {
			params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
			List<Branch> list = branchService.findByConditions(params);
			json.put("list", list);
			json.put("code", 0);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			json.put("code",1);
			json.put("message", e.getMessage());
		}
		return json;
	}


	@RequestMapping(value="/createProduct", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject createProduct(@RequestBody BranchProduct branchProduct, Model model){
		JSONObject result = new JSONObject();
		try {
			Long id = branchProductService.saveBranchProduct(branchProduct);
			result.put("code", 0);
			result.put("data", id);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="/branch/products")
	@ResponseBody
	public JSONObject branchProducts(@RequestBody QueryBranchProductParams params, Model model){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		PageList<BranchProductVo> pageList = branchProductService.findPageBranchProducts(params);
		json.put("list", pageList);
		json.put("total", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/branch/product/{id}")
	@ResponseBody
	public JSONObject branchProductDetail(@PathVariable Long id, Model model){
		JSONObject json = new JSONObject();
		BranchProductInfo bpInfo = branchProductService.getBranchProductInfoById(id);
		json.put("branchProductInfo", bpInfo);
		return json;
	}
}
