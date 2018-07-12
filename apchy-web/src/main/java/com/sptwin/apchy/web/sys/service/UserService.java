package com.sptwin.apchy.web.sys.service;

import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.sys.pojo.UserCustom;

public interface UserService {
    User findByUserName(String userName);

    void insertUser(UserCustom userCustom);
}
