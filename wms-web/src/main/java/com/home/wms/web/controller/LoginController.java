package com.home.wms.web.controller;


import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.home.wms.dto.CurrentUserInfo;
import com.home.wms.dto.LoginDto;
import com.home.wms.dto.WMSResponse;
import com.home.wms.entity.*;
import com.home.wms.service.OrganizationService;
import com.home.wms.service.PermissionService;
import com.home.wms.service.RoleService;
import com.home.wms.service.UserService;
import com.home.wms.utils.AppConstants;
import com.home.wms.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private OrganizationService organizationService;

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value="/login", method = RequestMethod.POST)
    @ResponseBody
    public WMSResponse login(@RequestHeader(required = false) String token, LoginDto loginDto, HttpSession session, HttpServletResponse res) {
        WMSResponse response = new WMSResponse();
        User user = null;
        if (StringUtils.isNotBlank(token)) {
            user = userService.findByToken(token);
            if (user == null) {
                response.setCode(1);
                response.setMsg("请重新登录！");
                return response;
            }
        } else {
            user = userService.findByEmailAndPwd(loginDto.getEmail(), loginDto.getPassword());
        }
        if (user != null) {
            Organization organization = null;
            if (user.getOrganizationId() != null) {
                organization = organizationService.getById(user.getOrganizationId());
                if (organization == null || organization.getStatus() == 0) {
                    response.setCode(1);
                    response.setMsg("机构不存在！");
                    return response;
                }
            }
            Role role = roleService.getById(user.getRoleId());
            if (role == null || role.getStatus() == 0) {
                response.setCode(1);
                response.setMsg("用户角色不存在！");
                return response;
            }

            if (StringUtils.isBlank(user.getToken())) {
                String newToken = MyUtils.generateToken(user.getEmail(), user.getPassword(), user.getOrganizationId());
                user.setToken(newToken);
                userService.update(user);
            } else {//token 不为空，验证token 是否已经改变
                String newToken = MyUtils.generateToken(user.getEmail(), user.getPassword(), user.getOrganizationId());
                if(!StringUtils.equals(newToken, user.getToken())) {
                    user.setToken(newToken);
                    userService.update(user);
                    response.setCode(1);
                    response.setMsg("令牌失效，请重新登录");
                    return response;
                }

            }

            CurrentUserInfo currentUserInfo = new CurrentUserInfo();
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
            // 设置crm登录token的cookie
            Cookie cookie = new Cookie("wms_token", user.getToken());
//            cookie.setDomain("");
            cookie.setMaxAge(Integer.MAX_VALUE);
            cookie.setPath("/");
            res.addCookie(cookie);
        } else {
            response.setCode(1);
            response.setMsg("用户名或者密码不正确！");
        }
        return response;
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value="/main", method = RequestMethod.GET)
    public String main(HttpSession session, Model model) {
        CurrentUserInfo currentUserInfo = (CurrentUserInfo)session.getAttribute(AppConstants.CURRENT_USER);
        List<RolePermission> rolePermissionList = roleService.findRolePermissionByRoleId(currentUserInfo.getRoleId());
        if (rolePermissionList != null && rolePermissionList.size() > 0) {
            List<Long> permissionIds = Lists.newArrayListWithCapacity(rolePermissionList.size());
            for (RolePermission rolePermission : rolePermissionList) {
                permissionIds.add(rolePermission.getPermissionId());
            }
            List<Permission> permissions = permissionService.findPermissionsByIds(permissionIds);
            model.addAttribute("permissionTrees", permissionService.buildPermissionTree(permissions));
        }
        model.addAttribute("currentUserInfo", JSON.toJSONString(currentUserInfo));
        return "main";
    }

    @RequestMapping(value="/logout", method = RequestMethod.POST)
    @ResponseBody
    public WMSResponse logout(HttpSession session) {
        session.invalidate();
        WMSResponse response = new WMSResponse();
        return response;
    }


}
