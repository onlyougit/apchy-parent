package com.sptwin.apchy.web.sys.service;

import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.sys.pojo.UserCustom;
import com.sptwin.spchy.model.common.ResponseJson;

public interface UserService {
    User findByUserName(String userName);

    void insertUser(UserCustom userCustom);

    ResponseJson<Object>  changePassword(UserCustom userCustom);
}
