package com.home.wms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.home.wms.dto.QueryUserParams;
import com.home.wms.dto.UserVo;
import com.home.wms.entity.User;
import com.home.wms.service.UserService;
import com.home.wms.utils.AppContextManager;
import com.ktanx.common.model.PageList;
import com.ktanx.jdbc.command.entity.Select;
import com.ktanx.jdbc.persist.JdbcDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private JdbcDao jdbcDao;
    
    public PageList<UserVo> findPageUsers(QueryUserParams params) {
	    StringBuffer sql = new StringBuffer("select user.*, role.name role_name, role.code role_code, organization.name organization_name from user " +
			    "left join role on user.role_id = role.id " +
			    "left join organization on user.organization_id = organization.id where 1 ");
	    List<Object> values = Lists.newArrayList();
	    Long organizationId = params.getOrganizationId() != null ? params.getOrganizationId() : AppContextManager.getCurrentUserInfo().getOrganizationId();
	    if (organizationId != null) {
		    sql.append(" and user.organization_id = ?");
		    values.add(organizationId);
	    }
	    if (StringUtils.isNotBlank(params.getName())) {
		    sql.append(" and user.name like ?");
		    values.add("%" + params.getName().trim() + "%");
	    }
	    if (params.getRoleCodes() != null && params.getRoleCodes().size() > 0) {
	    	String roleStr = "";
	    	for (String s : params.getRoleCodes()) {
	    		roleStr += "'" + s +  "',";
		    }
		    if (StringUtils.isNotBlank(roleStr)) {
	    		roleStr = roleStr.substring(0, roleStr.length() - 1);
		    }
		    sql.append(StrUtil.format(" and role.code in ({})", roleStr));
	    }
	    sql.append(" order by user.id desc");
	    return (PageList<UserVo>)jdbcDao.createNativeExecutor().resultClass(UserVo.class).command(sql.toString()).forceNative(true).parameters(values.toArray()).pageList(params.getiDisplayStart()/params.getiDisplayLength() + 1, params.getiDisplayLength());
    }
    
    public void save(User user) {
	    Long organizationId = AppContextManager.getCurrentUserInfo().getOrganizationId();
    	if (organizationId != null) {
		    user.setOrganizationId(organizationId);
	    }
        jdbcDao.insert(user);
    }
    
    public void delete(long id) {
        jdbcDao.delete(User.class, id);
    }
    
    public void update(User user) {
        jdbcDao.update(user);
    }
    
    public User getById(long id) {
        return jdbcDao.get(User.class, id);
    }
    
    public User findByEmailAndPwd(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return jdbcDao.querySingleResult(user);
    }

    public User findByToken(String token) {
    	User user = new User();
    	user.setToken(token);
    	return jdbcDao.querySingleResult(user);
    }
    public List<User> findUsers(User user) {
    	return jdbcDao.queryList(user);
    }

	public List<User> findUsersNotInEngineer() {
		StringBuffer sql = new StringBuffer("select * from user");
		sql.append(" where NOT EXISTS (select 1 from engineer where user_id = user.id)");
		sql.append(" and user.role_id = (select id from role where code ='engineer' limit 1)");
		Long organizationId = AppContextManager.getCurrentUserInfo().getOrganizationId();
		if (organizationId != null) {
			sql.append(" and user.organization_id = " + organizationId);
		}
		return (List<User>)jdbcDao.createNativeExecutor().resultClass(User.class).command(sql.toString()).forceNative(true).list();

	}
	public List<User> findUsersNotInBranch() {
		StringBuffer sql = new StringBuffer("select * from user");
		sql.append(" where NOT EXISTS (select 1 from branch where user_id = user.id)");
		sql.append(" and user.role_id = (select id from role where code ='customer' limit 1)");
		Long organizationId = AppContextManager.getCurrentUserInfo().getOrganizationId();
		if (organizationId != null) {
			sql.append(" and user.organization_id = " + organizationId);
		}
		return (List<User>)jdbcDao.createNativeExecutor().resultClass(User.class).command(sql.toString()).forceNative(true).list();

	}
}
