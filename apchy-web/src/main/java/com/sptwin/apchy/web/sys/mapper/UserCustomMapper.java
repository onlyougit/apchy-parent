package com.sptwin.apchy.web.sys.mapper;

import com.sptwin.apchy.web.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserCustomMapper {

    @Select("select id, user_name userName, password, salt, locked from t_sys_user where user_name = #{userName}")
    User findByUserName(String userName);
}
