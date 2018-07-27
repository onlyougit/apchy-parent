package com.sptwin.apchy.web.sys.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleResourceCustomMapper {
    @Delete("delete from t_sys_role_resource where resource_id=#{value}")
    void deleteByResourceId(Long id);

    @Select("select resource_id from t_sys_role_resource where role_id=#{value}")
    List<Long> queryResourceByRoleId(Long roleId);
}