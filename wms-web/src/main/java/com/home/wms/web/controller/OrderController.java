package com.home.wms.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
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

import java.util.Date;

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
	public JSONObject loadData(QueryOrderParams params){
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
		model.addAttribute("types",dictService.findByType(DictType.PROBLEM_TYPE.getValue()));
		return "/order/add";
	}

	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(Torder order){
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
	public JSONObject update(@PathVariable Long id){
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
	public String assign(@PathVariable Long id){
		return "/order/assign_vendor";
	}

	@RequestMapping(value="/assign/vendor", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject assignVendor(@RequestParam Long orderId,@RequestParam Long vendorId, String privateOrder){
		JSONObject result = new JSONObject();
		Torder order = new Torder();
		order.setStatus(OrderStatus.CHECKING.getValue());
		order.setCheckTime(new Date());
		order.setId(orderId);
		order.setVendorId(vendorId);
		order.setPrivateOrder(privateOrder);
		try {
			Torder torder = orderService.getOrderById(orderId);
			if (torder != null && torder.getVendorId() != null) {
				result.put("code", 1);
				result.put("message", "This order has assigned");
				return result;
			}
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

	@RequestMapping(value="/check/{id}", method = RequestMethod.GET)
	public String check(@PathVariable Long id, Model model){
		model.addAttribute("order",orderService.getOrderById(id));
		model.addAttribute("types",dictService.findByType(DictType.PROBLEM_TYPE.getValue()));
		return "/order/check";
	}

	@RequestMapping(value="/checked", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject checked(@RequestParam Long orderId,@RequestParam Integer type,@RequestParam String description, Model model){
		JSONObject result = new JSONObject();
		Torder order = new Torder();
		order.setStatus(OrderStatus.FIXING.getValue());
		order.setFixTime(new Date());
		order.setId(orderId);
		order.setType(type);
		order.setDescription(description);
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

	@RequestMapping(value="/fix/{id}", method = RequestMethod.GET)
	public String fix(@PathVariable Long id, Model model){
		model.addAttribute("order",orderService.getOrderVoById(id));
		return "/order/fix";
	}

	@RequestMapping(value="/fixed", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject fixed(@RequestParam Long orderId, String fixRemark){
		JSONObject result = new JSONObject();
		Torder order = new Torder();
//		order.setStatus(OrderStatus.AUDITING.getValue());
		order.setStatus(OrderStatus.COMPLETE.getValue());
		order.setCompleteTime(new Date());
		order.setFixRemark(fixRemark);
		order.setId(orderId);
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

	@RequestMapping(value="/audited", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject audited(@RequestParam Long orderId, @RequestParam String remark){
		JSONObject result = new JSONObject();
		Torder order = new Torder();
		order.setStatus(OrderStatus.COMPLETE.getValue());
		order.setRemark(remark);
		order.setId(orderId);
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

	@RequestMapping(value="/reject/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject reject(@PathVariable Long id){
		JSONObject result = new JSONObject();
		Torder order = orderService.getOrderById(id);
		order.setStatus(OrderStatus.ASSIGNING.getValue());
		order.setVendorId(null);
		order.setCheckTime(null);
		try {
			orderService.updateWithNull(order);
			result.put("code", 0);
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.put("code", 1);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@RequestMapping(value="/feedback", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject feedback(@RequestParam Long orderId,@RequestParam Long vendorId,@RequestParam Integer score,@RequestParam String feedback){
		JSONObject result = new JSONObject();
		try {
			orderService.feedback(orderId, vendorId, score, feedback);
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
