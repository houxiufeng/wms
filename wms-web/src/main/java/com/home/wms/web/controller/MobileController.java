package com.home.wms.web.controller;

import com.alibaba.fastjson.JSON;
import com.home.wms.dto.*;
import com.home.wms.entity.*;
import com.home.wms.enums.DictType;
import com.home.wms.enums.OrderStatus;
import com.home.wms.service.*;
import com.home.wms.utils.AppConstants;
import com.home.wms.utils.AppContextManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by fitz on 2018/3/17.
 */
@Controller
@RequestMapping("/mobile")
public class MobileController {

	@Autowired
	private VendorService vendorService;
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

	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpSession session, Model model) {
		CurrentUserInfo currentUserInfo = (CurrentUserInfo)session.getAttribute(AppConstants.CURRENT_USER);
		model.addAttribute("currentUserInfo", JSON.toJSONString(currentUserInfo));
		return "/mobile/index";
	}
	@RequestMapping(value = "/vendor", method = RequestMethod.GET)
	public String vendor(HttpSession session, Model model) {
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		Vendor vendor = vendorService.getVendorByUserId(userId);
		if (vendor != null) {
			model.addAttribute("vendor",vendor);
			Torder order = new Torder();
			order.setOrganizationId(AppContextManager.getCurrentUserInfo().getOrganizationId());
			order.setVendorId(vendor.getId());
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
		return "/mobile/vendor_index";
	}

	@RequestMapping(value = "/vendor/order/list", method = RequestMethod.GET)
	public String vendorOrderCheckList(@RequestParam Integer status,  Model model) {
		Long userId = AppContextManager.getCurrentUserInfo().getId();
		Vendor vendor = vendorService.getVendorByUserId(userId);
		model.addAttribute("vendor",vendor);
		model.addAttribute("status",status);
		return "mobile/vendor_order_list";
	}

	@RequestMapping(value="/vendor/order/detail/{id}", method = RequestMethod.GET)
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
		return "/mobile/vendor_order_detail";
	}

	@RequestMapping(value = "/branch/product/{id}", method = RequestMethod.GET)
	public String vendorOrderCheck(@CookieValue(value="wms_token",required=false) String token, HttpSession session, @PathVariable Long id,Model model) {
		CurrentUserInfo currentUserInfo = (CurrentUserInfo)session.getAttribute(AppConstants.CURRENT_USER);
		if (session.getAttribute(AppConstants.CURRENT_USER) == null) {//如果没登录
			if (StringUtils.isBlank(token)) {
				return "redirect:/login";
			}
			User user = userService.findByToken(token);
			if (user == null) {
				return "redirect:/login";
			} else {
				Organization organization = null;
				if (user.getOrganizationId() != null) {
					organization = organizationService.getById(user.getOrganizationId());
					if (organization == null || organization.getStatus() == 0) {
						throw new RuntimeException("机构不存在");
					}
				}
				Role role = roleService.getById(user.getRoleId());
				if (role == null || role.getStatus() == 0) {
					throw new RuntimeException("角色不存在");
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
		}

		AppContextManager.setCurrentUserInfo(currentUserInfo);
		BranchProductInfo bp = branchProductService.getBranchProductInfoById(id);
		QueryOrderParams params = new QueryOrderParams();
		params.setOrganizationId(currentUserInfo.getOrganizationId());
		Vendor vendor = vendorService.getVendorByUserId(currentUserInfo.getId());
		if (vendor != null) {
			params.setVendorId(vendor.getId());
		} else {
			params.setVendorId(-1L);
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
		model.addAttribute("branchProduct", bp);
		model.addAttribute("types",dictService.findByType(DictType.PROBLEM_TYPE.getValue()));
		model.addAttribute("currentUserInfo", JSON.toJSONString(currentUserInfo));
		return "/mobile/vendor_order_check";
	}

	@RequestMapping(value="/vendor/order/fixed/{id}", method = RequestMethod.GET)
	public String orderFixed(@PathVariable Long id, Model model){
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
		return "/mobile/vendor_order_fixed";
	}

	@RequestMapping(value = "/branch", method = RequestMethod.GET)
	public String branch(Model model) {
		return "/mobile/register_index";
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

}
