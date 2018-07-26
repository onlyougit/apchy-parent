package com.sptwin.apchy.web.sys.mapper;

import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.model.UserCustom;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserCustomMapper {

    @Select("select id, user_name userName,real_name realName, password, salt, locked,gmt_create gmtCreate from t_sys_user where user_name = #{value}")
    User findByUserName(String userName);

    List<UserCustom> queryUser(UserCustom userCustom);

    @Select("select count(1) from t_sys_user where user_name = #{userName}")
    int isExistUserName(String userName);

    int insertSelective(UserCustom userCustom);

    UserCustom queryUserById(Long id);
}
