package com.home.wms.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.*;
import com.home.wms.entity.Branch;
import com.home.wms.entity.BranchProduct;
import com.home.wms.entity.Product;
import com.home.wms.enums.DictType;
import com.home.wms.service.*;
import com.home.wms.utils.AppContextManager;
import com.ktanx.common.model.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by fitz on 2018/3/4.
 */
@Controller
@RequestMapping("/branch/product")
public class BranchProductController {
	@Autowired
	private BranchProductService branchProductService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private ProductService productService;
	@Autowired
	private DictService dictService;

	private static final Logger LOG = LoggerFactory.getLogger(BranchProductController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, @RequestParam Long branchId){
//		Branch branch = branchService.getBranchById(branchId);
		QueryDictParams params = new QueryDictParams();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		params.setType(DictType.PRODUCT_BRAND.getValue());
		params.setiDisplayLength(1000);
		model.addAttribute("brands",dictService.findPageDicts(params));
		model.addAttribute("branchId", branchId);
		return "/branch/branch_product";
	}

	@RequestMapping("loadData")
	@ResponseBody
	public JSONObject loadData(QueryBranchProductParams params, Model model){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		PageList<BranchProductVo> pageList = branchProductService.findPageBranchProducts(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(BranchProduct branchProduct, Model model){
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

	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(BranchProduct branchProduct, Model model){
		JSONObject result = new JSONObject();
		try {
			branchProductService.updateBranchProduct(branchProduct);
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
			branchProductService.deleteBranchProduct(id);
			result.put("code", 0);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String getBranchProduct(@PathVariable Long id, Model model){
		try {
			BranchProduct bp = branchProductService.getBranchProductById(id);
			model.addAttribute("branchProduct", bp);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return "/branch/branch_product_detail";
	}

	@RequestMapping(value="/findByConditions", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject findByConditions(QueryBranchProductParams params){
		JSONObject result = new JSONObject();
		try {
			params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
			params.setiDisplayStart(0);
			params.setiDisplayLength(Integer.MAX_VALUE);
			PageList<BranchProductVo> pageList = branchProductService.findPageBranchProducts(params);
			result.put("code", 0);
			result.put("data", pageList);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}
}
