package com.home.wms.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.home.wms.dto.*;
import com.home.wms.entity.*;
import com.home.wms.enums.DictType;
import com.home.wms.enums.OrderStatus;
import com.home.wms.enums.RoleCode;
import com.home.wms.service.*;
import com.home.wms.utils.AppConstants;
import com.home.wms.utils.AppContextManager;
import com.home.wms.utils.MyUtils;
import com.ktanx.common.model.PageList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by fitz on 2018/3/17.
 */
@Controller
@RequestMapping("/mobile")
public class MobileController {

	@Autowired
	private EngineerService engineerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private BranchProductService branchProductService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DictService dictService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private CustomerService customerService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpSession session, Model model) {
		CurrentUserInfo currentUserInfo = (CurrentUserInfo)session.getAttribute(AppConstants.CURRENT_USER);
		model.addAttribute("currentUserInfo", JSON.toJSONString(currentUserInfo));
		return "/mobile/index";
	}
	@RequestMapping(value = "/engineer", method = RequestMethod.GET)
	public String engineer(HttpSession session, Model model) {
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		Engineer engineer = engineerService.getEngineerByUserId(userId);
		if (engineer != null) {
			model.addAttribute("engineer", engineer);
			Torder order = new Torder();
			order.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
			order.setEngineerId(engineer.getId());
			List<Torder> torders = orderService.findOrders(order);
			int checkAmount = 0;
			int fixAmount = 0;
			int completeAmount = 0;
			for (Torder item :torders) {
				if (item.getStatus() == 1) {
                    checkAmount++;
				} else if (item.getStatus() == 2) {
                    fixAmount++;
				} else if (item.getStatus() == 4) {
					completeAmount++;
				}
			}
			model.addAttribute("checkAmount", checkAmount);
			model.addAttribute("fixAmount", fixAmount);
			model.addAttribute("completeAmount", completeAmount);
		}
		return "/mobile/engineer_index";
	}

	@RequestMapping(value = "/engineer/order/list", method = RequestMethod.GET)
	public String engineerOrderCheckList(@RequestParam Integer status,  Model model) {
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		Engineer engineer = engineerService.getEngineerByUserId(userId);
		model.addAttribute("engineer", engineer);
		model.addAttribute("status",status);
		return "mobile/engineer_order_list";
	}

	@RequestMapping(value="/engineer/order/detail/{id}", method = RequestMethod.GET)
	public String orderDetail(@PathVariable Long id, Model model){
		try {
			OrderVo order = orderService.getOrderVoById(id);
			model.addAttribute("order", order);
			if (order != null) {
				BranchProductInfo bp = branchProductService.getBranchProductInfoById(order.getBranchProductId());
				model.addAttribute("branchProduct", bp);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "/mobile/engineer_order_detail";
	}

	@RequestMapping(value = "/branch/product/{id}", method = RequestMethod.GET)
	public String engineerOrderCheck(@CookieValue(value="wms_token",required=false) String token, HttpSession session, @PathVariable Long id,Model model,HttpServletResponse res) {
		if (StringUtils.isBlank(token)) {
			return "redirect:/login?fromUrl=/mobile/branch/product/"+id;
		}
		User user = userService.findByToken(token);
		if (user == null) {
			//清除cookie
			Cookie cookie = new Cookie("wms_token", "");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			res.addCookie(cookie);
			return "redirect:/login?fromUrl=/mobile/branch/product/"+id;
		}

		String newToken = MyUtils.generateToken(user.getEmail(), user.getPassword(), user.getOrganizationId());
		if(!StringUtils.equals(newToken, user.getToken())) {
			user.setToken(newToken);
			userService.update(user);
			//清除cookie
			Cookie cookie = new Cookie("wms_token", "");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			res.addCookie(cookie);
			return "redirect:/login?fromUrl=/mobile/branch/product/"+id;
		}
		CurrentUserInfo currentUserInfo = (CurrentUserInfo)session.getAttribute(AppConstants.CURRENT_USER);
		if (currentUserInfo == null) {//如果没登录
			Organization organization = null;
			if (user.getOrganizationId() != null) {
				organization = organizationService.getById(user.getOrganizationId());
				if (organization == null || organization.getStatus() == 0) {
					throw new RuntimeException("Organization does't exist!");
				}
			}
			Role role = roleService.getById(user.getRoleId());
			if (role == null || role.getStatus() == 0) {
				throw new RuntimeException("Role does't exist!");
			}

			currentUserInfo = new CurrentUserInfo();
			currentUserInfo.setId(user.getId());
			currentUserInfo.setName(user.getName());
			currentUserInfo.setEmail(user.getEmail());
			currentUserInfo.setRoleId(role.getId());
			currentUserInfo.setRoleName(role.getName());
			currentUserInfo.setRoleCode(role.getCode());
			if (organization != null) {
				currentUserInfo.setOrganizationId(organization.getId());
				currentUserInfo.setOrganizationName(organization.getName());
			}
			session.setAttribute(AppConstants.CURRENT_USER, currentUserInfo);
		}

		AppContextManager.setCurrentUserInfo(currentUserInfo);
		BranchProductInfo bp = branchProductService.getBranchProductInfoById(id);

		String page = "/mobile/error";
		if (StringUtils.equals(currentUserInfo.getRoleCode(), RoleCode.ENGINEER.getCode())) {//如果是维修员角色
			QueryOrderParams params = new QueryOrderParams();
			params.setOrganizationId(currentUserInfo.getOrganizationId());
			Engineer engineer = engineerService.getEngineerByUserId(currentUserInfo.getId());
			if (engineer != null) {
				params.setEngineerId(engineer.getId());
			} else {
				params.setEngineerId(-1L);
			}
			params.setStatus(OrderStatus.CHECKING.getValue());
			params.setiDisplayLength(Integer.MAX_VALUE);
			List<OrderVo> orders = orderService.findPageOrders(params);
			Torder order = null;
			for (Torder item : orders) {//检查当前客户名下有没有这个需要维修的产品订单
				if (item.getBranchProductId().longValue() == id.longValue()) {
					order = item;
					break;
				}
			}
			model.addAttribute("order", order);
			page = "/mobile/engineer_order_check";
		} else if (StringUtils.equals(currentUserInfo.getRoleCode(), RoleCode.CUSTOMER.getCode())) {
			Long customerId = bp.getBranch().getCustomerId();
			Customer customer = customerService.getCustomerById(customerId);
			model.addAttribute("customer", customer);
			page = "/mobile/order_add";
		}
		model.addAttribute("types",dictService.findByType(DictType.PROBLEM_TYPE.getValue()));
		model.addAttribute("branchProduct", bp);
		model.addAttribute("currentUserInfo", JSON.toJSONString(currentUserInfo));
		return page;
	}

	@RequestMapping(value = "/branch/list", method = RequestMethod.GET)
	public String branchList(Model model) {
		return "/mobile/branch_list";
	}

	@RequestMapping(value = "/branch/product/add", method = RequestMethod.GET)
	public String branchProductAdd(Model model, @RequestParam Long branchId){
		QueryDictParams params = new QueryDictParams();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		params.setType(DictType.PRODUCT_BRAND.getValue());
		params.setiDisplayLength(1000);
		model.addAttribute("brands",dictService.findPageDicts(params));
		model.addAttribute("branchId", branchId);
		return "/mobile/branch_product_add";
	}
	@RequestMapping(value = "/branch/product/list", method = RequestMethod.GET)
	public String branchProductList(Model model, @RequestParam Long branchId){
		model.addAttribute("branch", branchService.getBranchById(branchId));
		return "/mobile/branch_product_list";
	}
	@RequestMapping(value = "/branch/product/edit", method = RequestMethod.GET)
	public String branchProductEdit(Model model, @RequestParam Long branchProductId){
		QueryDictParams params = new QueryDictParams();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		params.setType(DictType.PRODUCT_BRAND.getValue());
		params.setiDisplayLength(1000);
		model.addAttribute("brands",dictService.findPageDicts(params));
		model.addAttribute("bp", branchProductService.getBranchProductInfoById(branchProductId));
		return "/mobile/branch_product_edit";
	}

	@RequestMapping(value = "/order/list", method = RequestMethod.GET)
	public String orderList(@RequestParam Integer flag,  Model model) {//flag==1 未评价；flag==2 已评价或已取消
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		Branch branch = branchService.getBranchByUserId(userId);
		if (branch != null) {
			model.addAttribute("branchId",branch.getId());
		} else {
			model.addAttribute("branchId",-1);
		}
		model.addAttribute("flag",flag);
		return "mobile/order_list";
	}

	@RequestMapping(value="/order/detail/{id}", method = RequestMethod.GET)
	public String customerOrderDetail(@PathVariable Long id, Model model){
		try {
			OrderVo order = orderService.getOrderVoById(id);
			model.addAttribute("order", order);
			if (order != null) {
				Engineer engineer = engineerService.getEngineerById(order.getEngineerId());
				model.addAttribute("engineer", engineer);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "/mobile/order_detail";
	}

	@RequestMapping(value="/order/feedback/{id}", method = RequestMethod.GET)
	public String feedback(@PathVariable Long id, Model model){
		try {
			OrderVo order = orderService.getOrderVoById(id);
			model.addAttribute("order", order);
			if (order != null) {
				Engineer engineer = engineerService.getEngineerById(order.getEngineerId());
				model.addAttribute("engineer", engineer);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "/mobile/order_feedback";
	}

	@RequestMapping(value="/engineer/checkingOrder", method = RequestMethod.GET)
	public String checkingOrder(@RequestParam Long orderId, Model model){
		try {
			OrderInfo orderInfo = orderService.getOrderInfo(orderId);
			model.addAttribute("orderInfo", orderInfo);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "mobile/engineer_checking_order";
	}
	@RequestMapping(value="/engineer/fixingOrder", method = RequestMethod.GET)
	public String orderFixed(@RequestParam Long orderId, Model model){
		try {
			OrderInfo orderInfo = orderService.getOrderInfo(orderId);
			model.addAttribute("orderInfo", orderInfo);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "mobile/engineer_fixing_order";
	}

	@RequestMapping(value="/engineer/report", method = RequestMethod.GET)
	public String report(Model model){
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		Engineer engineer = engineerService.getEngineerByUserId(userId);
		if (engineer != null) {
			model.addAttribute("engineer", engineer);
			StatOrderVo vo = orderService.statRecent6MonthsOrders(engineer.getId());
			model.addAttribute("charData", vo);
		}
		return "mobile/engineer_report";
	}

	@RequestMapping(value="/engineer/report/sum", method = RequestMethod.GET)
	public String orderSum(Model model){
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		Engineer engineer = engineerService.getEngineerByUserId(userId);
		model.addAttribute("engineer", engineer);
		return "mobile/engineer_report_sum";
	}

	@RequestMapping("/engineer/orderSum")
	@ResponseBody
	public JSONObject orderSum(QueryEngineerOrderSum params){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		if (StringUtils.isNotBlank(params.getStartTime())) {
			params.setStartTime(params.getStartTime() + " 00:00:01");
		}
		if (StringUtils.isNotBlank(params.getEndTime())) {
			params.setEndTime(params.getEndTime() + " 23:59:59");
		}
		PageList<EngineerOrderSum> pageList = orderService.findEngineerOrderSum(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/engineer/report/monthList", method = RequestMethod.GET)
	public String monthList(Long engineerId, String monthBegin, String monthEnd, Model model){
		model.addAttribute("engineerId", engineerId);
		model.addAttribute("monthBegin", monthBegin);
		model.addAttribute("monthEnd", monthEnd);
		return "mobile/engineer_report_month_list";
	}


	@RequestMapping("/engineer/report/monthOrders")
	@ResponseBody
	public JSONObject monthOrders(QueryMonthOrderParams params){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		if (StringUtils.isNotBlank(params.getMonthBegin())) {
			params.setMonthBegin(params.getMonthBegin() + " 00:00:01");
		}
		if (StringUtils.isNotBlank(params.getMonthEnd())) {
			params.setMonthEnd(params.getMonthEnd() + " 23:59:59");
		}
		PageList<OrderVo> pageList = orderService.findMonthOrders(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/engineer/reportOrderDetail", method = RequestMethod.GET)
	public String reportOrderDetail(@RequestParam Long orderId, Long engineerId, String monthBegin, String monthEnd, Model model){
		try {
			OrderInfo orderInfo = orderService.getOrderInfo(orderId);
			model.addAttribute("orderInfo", orderInfo);
			model.addAttribute("engineerId", engineerId);
			model.addAttribute("monthBegin", monthBegin);
			model.addAttribute("monthEnd", monthEnd);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "mobile/engineer_report_order_detail";
	}

	@RequestMapping(value="/engineer/report/rate", method = RequestMethod.GET)
	public String orderRate(Model model){
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		Engineer engineer = engineerService.getEngineerByUserId(userId);
		model.addAttribute("engineer", engineer);
		return "mobile/engineer_report_rate";
	}

	@RequestMapping("/engineer/orderRate")
	@ResponseBody
	public JSONObject orderRate(QueryEngineerOrderSum params){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		if (StringUtils.isNotBlank(params.getStartTime())) {
			params.setStartTime(params.getStartTime() + " 00:00:01");
		}
		if (StringUtils.isNotBlank(params.getEndTime())) {
			params.setEndTime(params.getEndTime() + " 23:59:59");
		}
		PageList<EngineerOrderRate> pageList = orderService.findEngineerOrderRate(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/engineer/me", method = RequestMethod.GET)
	public String me(Model model){
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		User user = userService.getById(userId);
		if (user != null) {
			model.addAttribute("user", user);
		}
		EngineerVo engineer = engineerService.getEngineerVoByUserId(userId);
		if (engineer != null) {
			model.addAttribute("engineer", engineer);
		}
		return "mobile/engineer_me";
	}

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public String customer(Model model) {
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		Branch branch = branchService.getBranchByUserId(userId);
		if (branch != null) {
			model.addAttribute("branch", branch);
			Customer customer = customerService.getCustomerById(branch.getCustomerId());
			if (customer != null) {
				model.addAttribute("customer", customer);
			}
			Torder torder = new Torder();
			torder.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
			torder.setBranchId(branch.getId());
			List<Torder> torders = orderService.findOrders(torder);
			int fixingNum = 0;//维修中
			int needRateNum = 0;//待评价
			for (Torder item : torders) {
				if (item.getStatus() == 2) {//fixing status
					fixingNum++;
				} else if (item.getStatus() == 4 && item.getScore() == null) {//need rate
					needRateNum++;
				}
			}
			model.addAttribute("fixingNum",fixingNum);
			model.addAttribute("needRateNum",needRateNum);

		}
		return "/mobile/customer_index";
	}

	@RequestMapping(value = "/customer/order/list", method = RequestMethod.GET)
	public String customerOrderList(@RequestParam Integer status, Integer feedbackFlag,  Model model) {
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		Branch branch = branchService.getBranchByUserId(userId);
		model.addAttribute("branch",branch);
		model.addAttribute("status",status);
		model.addAttribute("feedbackFlag",feedbackFlag);//1.未评价。2.已评价
		return "mobile/customer_order_list";
	}

	@RequestMapping(value="/customer/fixingOrder", method = RequestMethod.GET)
	public String customerFixOrder(@RequestParam Long orderId, Model model){
		try {
			OrderInfo orderInfo = orderService.getOrderInfo(orderId);
			model.addAttribute("orderInfo", orderInfo);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "mobile/customer_fixing_order";
	}

	@RequestMapping(value="/customer/rateOrder", method = RequestMethod.GET)
	public String customerRateOrder(@RequestParam Long orderId, Model model){
		try {
			OrderInfo orderInfo = orderService.getOrderInfo(orderId);
			model.addAttribute("orderInfo", orderInfo);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "mobile/customer_rate_order";
	}

	@RequestMapping(value="/customer/report", method = RequestMethod.GET)
	public String customerReport(Model model){
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		Branch branch = branchService.getBranchByUserId(userId);
		if (branch != null) {
			StatOrderVo vo = orderService.statBranchRecent6MonthsOrders(branch.getId());
			model.addAttribute("charData", vo);
		}
		return "mobile/customer_report";
	}

	@RequestMapping(value="/customer/report/sum", method = RequestMethod.GET)
	public String customerReportSum(Model model){
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		Branch branch = branchService.getBranchByUserId(userId);
		model.addAttribute("branch", branch);
		return "mobile/customer_report_sum";
	}

	@RequestMapping("/customer/orderSum")
	@ResponseBody
	public JSONObject customerOrderSum(QueryCustomerOrderSum params){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		if (StringUtils.isNotBlank(params.getStartTime())) {
			params.setStartTime(params.getStartTime() + " 00:00:01");
		}
		if (StringUtils.isNotBlank(params.getEndTime())) {
			params.setEndTime(params.getEndTime() + " 23:59:59");
		}
		PageList<CustomerOrderSum> pageList = orderService.findCustomerOrderSum(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/customer/report/monthList", method = RequestMethod.GET)
	public String customerMonthList(Long branchId, String monthBegin, String monthEnd, Model model){
		model.addAttribute("branchId", branchId);
		model.addAttribute("monthBegin", monthBegin);
		model.addAttribute("monthEnd", monthEnd);
		return "mobile/customer_report_month_list";
	}

	@RequestMapping("/customer/report/monthOrders")
	@ResponseBody
	public JSONObject customerMonthOrders(QueryMonthOrderParams params){
		JSONObject json = new JSONObject();
		params.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
		if (StringUtils.isNotBlank(params.getMonthBegin())) {
			params.setMonthBegin(params.getMonthBegin() + " 00:00:01");
		}
		if (StringUtils.isNotBlank(params.getMonthEnd())) {
			params.setMonthEnd(params.getMonthEnd() + " 23:59:59");
		}
		PageList<OrderVo> pageList = orderService.findMonthOrders(params);
		json.put("aaData", pageList);
		json.put("iTotalRecords", pageList.getPager().getTotalItems());
		json.put("iTotalDisplayRecords", pageList.getPager().getTotalItems());
		return json;
	}

	@RequestMapping(value="/customer/reportOrderDetail", method = RequestMethod.GET)
	public String customerReportOrderDetail(@RequestParam Long orderId, Long branchId, String monthBegin, String monthEnd, Model model){
		try {
			OrderInfo orderInfo = orderService.getOrderInfo(orderId);
			model.addAttribute("orderInfo", orderInfo);
			model.addAttribute("branchId", branchId);
			model.addAttribute("monthBegin", monthBegin);
			model.addAttribute("monthEnd", monthEnd);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "mobile/customer_report_detail";
	}

	@RequestMapping(value="/customer/me", method = RequestMethod.GET)
	public String customerMe(Model model){
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		User user = userService.getById(userId);
		if (user != null) {
			model.addAttribute("user", user);
		}
		Branch branch = branchService.getBranchByUserId(userId);
		if (branch != null) {
			BranchVo branchVo = branchService.getBranchVoById(branch.getId());
			if (branchVo != null) {
				model.addAttribute("branch", branchVo);
			}
		}
		return "mobile/customer_me";
	}

}
