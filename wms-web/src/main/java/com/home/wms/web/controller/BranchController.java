package com.home.wms.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.QueryBranchParams;
import com.home.wms.dto.QueryCustomerParams;
import com.home.wms.entity.Branch;
import com.home.wms.entity.Customer;
import com.home.wms.service.BranchService;
import com.home.wms.service.CustomerService;
import com.home.wms.utils.AppContextManager;
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

/**
 * Created by fitz on 2018/3/4.
 */
@Controller
@RequestMapping("/branch")
public class BranchController {
	@Autowired
	private BranchService branchService;
	@Autowired
	private CustomerService customerService;

	private static final Logger LOG = LoggerFactory.getLogger(BranchController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		return "/branch/list";
	}

	@RequestMapping("loadData")
	@ResponseBody
	public JSONObject loadData(QueryBranchParams params, Model model){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		PageList<Branch> pageList = branchService.findPageBranchs(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String add(Model model){
		QueryCustomerParams params = new QueryCustomerParams();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		params.setiDisplayLength(Integer.MAX_VALUE);
		params.setiDisplayStart(0);
		model.addAttribute("customers", customerService.findPageCustomers(params));
		return "/branch/add";
	}

	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(Branch branch, Model model){
		JSONObject result = new JSONObject();
		try {
			branchService.saveBranch(branch);
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
		QueryCustomerParams params = new QueryCustomerParams();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		params.setiDisplayLength(Integer.MAX_VALUE);
		params.setiDisplayStart(0);
		model.addAttribute("customers", customerService.findPageCustomers(params));
		model.addAttribute("branch", branchService.getBranchById(id));
		return "/branch/edit";
	}

	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(Branch branch, Model model){
		JSONObject result = new JSONObject();
		try {
			branchService.updateBranch(branch);
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
			branchService.deleteBranch(id);
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
