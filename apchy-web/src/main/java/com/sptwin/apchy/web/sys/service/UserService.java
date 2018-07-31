package com.sptwin.apchy.web.sys.service;

import com.sptwin.apchy.web.model.UserCustom;
import com.sptwin.spchy.model.common.Pagination;
import com.sptwin.spchy.model.common.ResponseJson;

import java.util.Map;

public interface UserService {

    ResponseJson<Object>  changePassword(UserCustom userCustom);

    Map queryUser(UserCustom userCustom, Pagination grid);

    ResponseJson<Object> insertOrEditUser(UserCustom userCustom, ResponseJson<Object> responseJson);

    UserCustom editQuery(Long id);

    void deleteUser(Long id);
}
