package com.home.wms.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.QueryDictParams;
import com.home.wms.dto.QueryEngineerParams;
import com.home.wms.dto.EngineerVo;
import com.home.wms.entity.Dict;
import com.home.wms.entity.Engineer;
import com.home.wms.entity.Torder;
import com.home.wms.entity.User;
import com.home.wms.enums.DictType;
import com.home.wms.service.DictService;
import com.home.wms.service.OrderService;
import com.home.wms.service.UserService;
import com.home.wms.service.EngineerService;
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
@RequestMapping("/engineer")
public class EngineerController {
	@Autowired
	private EngineerService engineerService;
	@Autowired
	private DictService dictService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;

	private static final Logger LOG = LoggerFactory.getLogger(EngineerController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		return "engineer/list";
	}

	@RequestMapping("loadData")
	@ResponseBody
	public JSONObject loadData(QueryEngineerParams params, Model model){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		PageList<EngineerVo> pageList = engineerService.findPageEngineer(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String add(Model model){
		QueryDictParams dictParams = new QueryDictParams();
		dictParams.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		dictParams.setType(DictType.ENGINEER_LEVEL.getValue());
		dictParams.setiDisplayLength(100);
		List<Dict> customerLevels = dictService.findPageDicts(dictParams);
		dictParams.setType(DictType.MAINTAIN_SKILL.getValue());
		List<Dict> maintainSkills = dictService.findPageDicts(dictParams);
		model.addAttribute("engineerLevels", customerLevels);
		model.addAttribute("maintainSkills", maintainSkills);
		model.addAttribute("users", userService.findUsersNotInEngineer());
		return "/engineer/add";
	}

	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(Engineer engineer, Model model){
		JSONObject result = new JSONObject();
		try {
			if (engineer.getUserId() != null) {
				Engineer v = engineerService.getEngineerByUserId(engineer.getUserId());
				if (v != null) {
					result.put("code", 1);
					result.put("message", "user already bind in " + v.getName());
					return result;
				}
			}
			engineerService.saveEngineer(engineer);
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
		dictParams.setType(DictType.ENGINEER_LEVEL.getValue());
		dictParams.setiDisplayLength(100);
		List<Dict> customerLevels = dictService.findPageDicts(dictParams);
		dictParams.setType(DictType.MAINTAIN_SKILL.getValue());
		List<Dict> maintainSkills = dictService.findPageDicts(dictParams);
		model.addAttribute("engineerLevels", customerLevels);
		model.addAttribute("maintainSkills", maintainSkills);
		Engineer engineer = engineerService.getEngineerById(id);
		model.addAttribute("engineer", engineer);
		List<User> users = userService.findUsersNotInEngineer();
		if (engineer.getUserId() != null) {
			users.add(0, userService.getById(engineer.getUserId()));
		}
		model.addAttribute("users", users);
		return "/engineer/edit";
	}

	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(Engineer engineer, Model model){
		JSONObject result = new JSONObject();
		try {
			if (engineer.getUserId() != null) {
				Engineer v = engineerService.getEngineerByUserId(engineer.getUserId());
				if (v != null && v.getId().longValue() != engineer.getId().longValue()) {
					result.put("code", 1);
					result.put("message", "user already bind in " + v.getName());
					return result;
				}
			}
			engineerService.updateEngineer(engineer);
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
			Torder order = new Torder();
			order.setEngineerId(id);
			if(orderService.findOrders(order).size() > 0) {
				result.put("code", 1);
				result.put("message", "can't delete, already in used!");
				return result;
			}
			engineerService.deleteEngineer(id);
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
