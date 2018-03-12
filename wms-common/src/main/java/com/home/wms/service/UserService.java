package com.home.wms.service;

import com.home.wms.dto.QueryUserParams;
import com.home.wms.dto.UserVo;
import com.home.wms.entity.User;
import com.ktanx.common.model.PageList;

public interface UserService {
    
    public PageList<UserVo> findUsers(QueryUserParams params);
    
    public void save(User user);
    
    public void delete(long id);
    
    public void update(User user);
    
    public User getById(long id);
    
    public User findByEmailAndPwd(String name, String password);
}
