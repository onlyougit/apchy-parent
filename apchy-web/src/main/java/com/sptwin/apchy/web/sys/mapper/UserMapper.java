package com.sptwin.apchy.web.sys.mapper;

import com.sptwin.apchy.web.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from t_sys_user where id = #{id}")
    User findById(Long id);

    @Select("select * from t_sys_user where user_name = #{userName}")
    User findByUserName(String userName);
}
