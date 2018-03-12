package com.home.wms.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.QueryCustomerParams;
import com.home.wms.entity.Customer;
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
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		return "/customer/list";
	}

	@RequestMapping("loadData")
	@ResponseBody
	public JSONObject loadData(QueryCustomerParams params, Model model){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		PageList<Customer> pageList = customerService.findPageCustomers(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String add(Model model){
		return "/customer/add";
	}

	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(Customer customer, Model model){
		JSONObject result = new JSONObject();
		try {
			customerService.saveCustomer(customer);
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
		model.addAttribute("customer", customerService.getCustomerById(id));
		return "/customer/edit";
	}

	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(Customer customer, Model model){
		JSONObject result = new JSONObject();
		try {
			customerService.updateCustomer(customer);
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
			customerService.deleteCustomer(id);
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
