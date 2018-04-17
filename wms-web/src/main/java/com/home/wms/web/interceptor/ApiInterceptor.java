package com.home.wms.web.interceptor;

import com.home.wms.dto.CurrentUserInfo;
import com.home.wms.entity.Organization;
import com.home.wms.entity.Role;
import com.home.wms.entity.User;
import com.home.wms.service.OrganizationService;
import com.home.wms.service.RoleService;
import com.home.wms.service.UserService;
import com.home.wms.utils.AppContextManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by fitz on 2018/2/22.
 */
public class ApiInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OrganizationService organizationService;

	private static final Logger LOG = LoggerFactory.getLogger(ApiInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object o) throws Exception {
		String token = req.getHeader("token");
		if (StringUtils.isNotBlank(token)) {
			User user = userService.findByToken(token);
			if (user != null) {
				Organization organization = null;
				if (user.getOrganizationId() != null) {
					organization = organizationService.getById(user.getOrganizationId());
					if (organization == null || organization.getStatus() == 0) {
						LOG.error("organization is not exist!");
						res.setStatus(403);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out = res.getWriter();
						out.println("{\"code\":\"1\", \"msg\":\"organization is not exist\"}");
						return false;
					}
				}

				Role role = roleService.getById(user.getRoleId());
				if (role == null || role.getStatus() == 0) {
					res.setStatus(403);
					LOG.error("role is not exist!");
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.println("{\"code\":\"1\", \"msg\":\"role is not exist\"}");
					return false;
				}

				CurrentUserInfo currentUserInfo = new CurrentUserInfo();
				currentUserInfo.setId(user.getId());
				currentUserInfo.setName(user.getName());
				currentUserInfo.setEmail(user.getEmail());
				currentUserInfo.setRoleId(role.getId());
				currentUserInfo.setRoleName(role.getName());
				currentUserInfo.setRoleCode(role.getCode());
				currentUserInfo.setToken(user.getToken());
				if (organization != null) {
					currentUserInfo.setOrganizationId(organization.getId());
					currentUserInfo.setOrganizationName(organization.getName());
				}
				//把当前用户信息保存到threadlocal, 随时可取出来用
				AppContextManager.setCurrentUserInfo(currentUserInfo);
				return true;
			} else {
				res.setStatus(403);
				LOG.error("invalid token {}", token);
				res.setContentType("application/json;charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.println("{\"code\":\"1\", \"msg\":\"invalid token " + token + " \"}");
				return false;
			}
		} else {
			res.setStatus(403);
			LOG.error("token is not exist!");
			res.setContentType("application/json;charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.println("{\"code\":\"1\", \"msg\":\"token is not exist\"}");
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}
}
