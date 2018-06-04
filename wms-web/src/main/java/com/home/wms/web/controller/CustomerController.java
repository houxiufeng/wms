package com.home.wms.web.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.CustomerVo;
import com.home.wms.dto.QueryBranchParams;
import com.home.wms.dto.QueryCustomerParams;
import com.home.wms.entity.Branch;
import com.home.wms.entity.Customer;
import com.home.wms.entity.User;
import com.home.wms.enums.DictType;
import com.home.wms.service.BranchService;
import com.home.wms.service.CustomerService;
import com.home.wms.service.DictService;
import com.home.wms.service.UserService;
import com.home.wms.utils.AppContextManager;
import com.ktanx.common.model.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by fitz on 2018/3/4.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private DictService dictService;
	@Autowired
	private BranchService branchService;

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
		PageList<CustomerVo> pageList = customerService.findPageCustomers(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String add(Model model){
		model.addAttribute("types", dictService.findByType(DictType.CUSTOMER_LEVEL.getValue()));
		model.addAttribute("creditStatus", dictService.findByType(DictType.CUSTOMER_CREDIT.getValue()));
//		model.addAttribute("users", userService.findUsersNotInCustomer());
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
		model.addAttribute("types", dictService.findByType(DictType.CUSTOMER_LEVEL.getValue()));
		model.addAttribute("creditStatus", dictService.findByType(DictType.CUSTOMER_CREDIT.getValue()));
		Customer customer = customerService.getCustomerById(id);
		model.addAttribute("customer", customer);
//		List<User> users =  userService.findUsersNotInCustomer();
//		if (customer.getUserId() != null) {
//			users.add(0, userService.getById(customer.getUserId()));
//		}
//		model.addAttribute("users", users);
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
			QueryBranchParams params = new QueryBranchParams();
			params.setCustomerId(id);
			if(branchService.findByConditions(params).size() > 0) {
				result.put("code", 1);
				result.put("message", "can't delete, already in used!");
				return result;
			}
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

	@RequestMapping(value="/downloadTemplate", method = RequestMethod.GET)
	public String dowloadTemplate(@RequestParam("fileName") String fileName, HttpServletRequest request, HttpServletResponse response){
		if (fileName != null) {
			String realPath = request.getSession().getServletContext().getRealPath("/files");
			File file = new File(realPath, fileName);
			if (file.exists()) {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}
}
