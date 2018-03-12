package com.home.wms.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.QueryDictParams;
import com.home.wms.dto.QueryVendorParams;
import com.home.wms.dto.QueryCustomerParams;
import com.home.wms.dto.VendorVo;
import com.home.wms.entity.Dict;
import com.home.wms.entity.Vendor;
import com.home.wms.enums.DictType;
import com.home.wms.service.DictService;
import com.home.wms.service.VendorService;
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

import java.util.List;

/**
 * Created by fitz on 2018/3/4.
 */
@Controller
@RequestMapping("/vendor")
public class VendorController {
	@Autowired
	private VendorService vendorService;
	@Autowired
	private DictService dictService;

	private static final Logger LOG = LoggerFactory.getLogger(VendorController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		return "vendor/list";
	}

	@RequestMapping("loadData")
	@ResponseBody
	public JSONObject loadData(QueryVendorParams params, Model model){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		PageList<VendorVo> pageList = vendorService.findPageVendors(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String add(Model model){
		QueryDictParams dictParams = new QueryDictParams();
		dictParams.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		dictParams.setType(DictType.VENDOR_LEVEL.getValue());
		dictParams.setiDisplayLength(100);
		List<Dict> customerLevels = dictService.findPageDicts(dictParams);
		dictParams.setType(DictType.MAINTAIN_SKILL.getValue());
		List<Dict> maintainSkills = dictService.findPageDicts(dictParams);
		model.addAttribute("vendorLevels", customerLevels);
		model.addAttribute("maintainSkills", maintainSkills);
		return "/vendor/add";
	}

	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(Vendor vendor, Model model){
		JSONObject result = new JSONObject();
		try {
			vendorService.saveVendor(vendor);
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
		QueryDictParams dictParams = new QueryDictParams();
		dictParams.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		dictParams.setType(DictType.VENDOR_LEVEL.getValue());
		dictParams.setiDisplayLength(100);
		List<Dict> customerLevels = dictService.findPageDicts(dictParams);
		dictParams.setType(DictType.MAINTAIN_SKILL.getValue());
		List<Dict> maintainSkills = dictService.findPageDicts(dictParams);
		model.addAttribute("vendorLevels", customerLevels);
		model.addAttribute("maintainSkills", maintainSkills);
		model.addAttribute("vendor", vendorService.getVendorById(id));
		return "/vendor/edit";
	}

	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(Vendor vendor, Model model){
		JSONObject result = new JSONObject();
		try {
			vendorService.updateVendor(vendor);
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
			vendorService.deleteVendor(id);
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
