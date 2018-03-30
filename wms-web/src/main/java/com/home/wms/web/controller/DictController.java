package com.home.wms.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.home.wms.dto.DictVo;
import com.home.wms.dto.OrderTimeSetting;
import com.home.wms.dto.QueryDictParams;
import com.home.wms.dto.QueryCustomerParams;
import com.home.wms.entity.Branch;
import com.home.wms.entity.Dict;
import com.home.wms.entity.OrderTime;
import com.home.wms.enums.DictType;
import com.home.wms.service.DictService;
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
 * Created by fitz on 2018/3/10.
 */
@Controller
@RequestMapping("/dict")
public class DictController {
	@Autowired
	private DictService dictService;

	private static final Logger LOG = LoggerFactory.getLogger(DictController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		model.addAttribute("dictTypes", DictType.values());
		return "/dict/dict";
	}

	@RequestMapping("loadData")
	@ResponseBody
	public JSONObject loadData(QueryDictParams params, Model model){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		PageList<Dict> pageList = dictService.findPageDicts(params);
		List<DictVo> vos = Lists.newArrayListWithCapacity(pageList.size());
		for (Dict dict : pageList) {
			DictVo vo = new DictVo();
			BeanUtil.copyProperties(dict, vo);
			vos.add(vo);
		}
		json.put("aaData", vos);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}


	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(Dict dict, Model model){
		JSONObject result = new JSONObject();
		try {
			dictService.saveDict(dict);
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
			dictService.deleteDict(id);
			result.put("code", 0);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="/orderTime",method = RequestMethod.GET)
	public String orderTime(Model model){
		model.addAttribute("setting",dictService.findOrderTimeSetting(AppContextManager.getCurrentUserInfo().getOrganizationId()));
		return "/dict/order_time";
	}

	@RequestMapping(value="/orderTime/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateOrderTime(OrderTimeSetting orderTimeSetting, Model model){
		JSONObject result = new JSONObject();
		try {
			dictService.saveOrUpdateOrderTime(orderTimeSetting);
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
