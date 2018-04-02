package com.home.wms.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.QueryBranchProductParams;
import com.home.wms.dto.QueryDictParams;
import com.home.wms.dto.QueryProductParams;
import com.home.wms.entity.Dict;
import com.home.wms.entity.Product;
import com.home.wms.enums.DictType;
import com.home.wms.service.BranchProductService;
import com.home.wms.service.DictService;
import com.home.wms.service.ProductService;
import com.home.wms.utils.AppContextManager;
import com.ktanx.common.model.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by fitz on 2018/3/4.
 */
@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private DictService dictService;
	@Autowired
	private BranchProductService branchProductService;

	private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		return "/product/list";
	}

	@RequestMapping("loadData")
	@ResponseBody
	public JSONObject loadData(QueryProductParams params, Model model){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		PageList<Product> pageList = productService.findPageProducts(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String add(Model model){
		QueryDictParams params = new QueryDictParams();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		params.setType(DictType.PRODUCT_BRAND.getValue());
		model.addAttribute("brands",dictService.findPageDicts(params));
		return "/product/add";
	}

	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(Product product, Model model){
		JSONObject result = new JSONObject();
		try {
			productService.save(product);
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
		QueryDictParams params = new QueryDictParams();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		params.setType(DictType.PRODUCT_BRAND.getValue());
		model.addAttribute("brands",dictService.findPageDicts(params));
		model.addAttribute("product", productService.getVoById(id));
		return "/product/edit";
	}

	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(Product product, Model model){
		JSONObject result = new JSONObject();
		try {
			productService.update(product);
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
			QueryBranchProductParams params = new QueryBranchProductParams();
			params.setProductId(id);
			if (branchProductService.findPageBranchProducts(params).size() > 0) {
				result.put("code", 1);
				result.put("message", "can't delete, already in used!");
				return result;
			}
			productService.delete(id);
			result.put("code", 0);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="/addBrand", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addBrand(@RequestParam String brandName){
		JSONObject result = new JSONObject();
		try {
			Dict dict = new Dict();
			dict.setType(DictType.PRODUCT_BRAND.getValue());
			dict.setName(brandName);
			dictService.saveDict(dict);
			result.put("code", 0);
			result.put("data",dict);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="/findByConditions", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject findByConditions(QueryProductParams params){
		JSONObject result = new JSONObject();
		try {
			Product p = new Product();
			p.setName(params.getName());
			p.setModel(params.getModel());
			p.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
			result.put("products",productService.findByConditions(p));
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
