package com.home.wms.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.OrderVo;
import com.home.wms.dto.QueryCustomerParams;
import com.home.wms.dto.QueryDictParams;
import com.home.wms.dto.QueryOrderParams;
import com.home.wms.entity.Torder;
import com.home.wms.enums.DictType;
import com.home.wms.enums.OrderStatus;
import com.home.wms.service.CustomerService;
import com.home.wms.service.DictService;
import com.home.wms.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private DictService dictService;

	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		return "/order/list";
	}

	@RequestMapping("loadData")
	@ResponseBody
	public JSONObject loadData(QueryOrderParams params, Model model){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		PageList<OrderVo> pageList = orderService.findPageOrders(params);
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
		QueryDictParams dictParams = new QueryDictParams();
		dictParams.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		dictParams.setType(DictType.PROBLEM_TYPE.getValue());
		dictParams.setiDisplayStart(0);
		dictParams.setiDisplayLength(Integer.MAX_VALUE);
		model.addAttribute("types", dictService.findPageDicts(dictParams));
		return "/order/add";
	}

	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(Torder order, Model model){
		JSONObject result = new JSONObject();
		try {
			orderService.saveOrder(order);
			result.put("code", 0);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="/cancel/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(@PathVariable Long id, Model model){
		JSONObject result = new JSONObject();
		Torder order = new Torder();
		order.setStatus(OrderStatus.CANCEL.getValue());
		order.setId(id);
		try {
			orderService.updateOrder(order);
			result.put("code", 0);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable Long id, Model model){
		try {
			OrderVo vo = orderService.getOrderVoById(id);
			model.addAttribute("order", vo);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return "/order/detail";
	}

	@RequestMapping(value="/assign/{id}", method = RequestMethod.GET)
	public String assign(@PathVariable Long id, Model model){
		model.addAttribute("orderId", id);
		return "/order/assign_vendor";
	}

	@RequestMapping(value="/assign/vendor", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject assignVendor(@RequestParam Long orderId,@RequestParam Long vendorId, Model model){
		JSONObject result = new JSONObject();
		Torder order = new Torder();
		order.setStatus(OrderStatus.CHECKING.getValue());
		order.setId(orderId);
		order.setVendorId(vendorId);
		try {
			orderService.updateOrder(order);
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
