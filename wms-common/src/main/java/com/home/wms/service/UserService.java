package com.home.wms.service;

import com.home.wms.dto.QueryUserParams;
import com.home.wms.dto.UserVo;
import com.home.wms.entity.User;
import com.ktanx.common.model.PageList;

public interface UserService {
    
    PageList<UserVo> findUsers(QueryUserParams params);
    
    void save(User user);
    
    void delete(long id);
    
    void update(User user);
    
    User getById(long id);
    
    User findByEmailAndPwd(String name, String password);

    User findByToken(String token);

}
