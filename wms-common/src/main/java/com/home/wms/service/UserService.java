package com.home.wms.service;

import com.home.wms.dto.QueryUserParams;
import com.home.wms.dto.UserVo;
import com.home.wms.entity.User;
import com.ktanx.common.model.PageList;

import java.util.List;

public interface UserService {
    
    PageList<UserVo> findPageUsers(QueryUserParams params);
    
    void save(User user);
    
    void delete(long id);
    
    void update(User user);
    
    User getById(long id);
    
    User findByEmailAndPwd(String name, String password);

    User findByToken(String token);

    List<User> findUsers(User user);

    List<User> findUsersNotInEngineer();

    List<User> findUsersNotInBranch();

}
