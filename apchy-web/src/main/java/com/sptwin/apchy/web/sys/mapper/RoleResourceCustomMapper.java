package com.sptwin.apchy.web.sys.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Set;

public interface RoleResourceCustomMapper {
    @Delete("delete from t_sys_role_resource where resource_id=#{value}")
    void deleteByResourceId(Long id);

    @Select("select resource_id from t_sys_role_resource where role_id=#{value}")
    List<Long> queryResourceByRoleId(Long roleId);

    @Delete("delete from t_sys_role_resource where role_id=#{value}")
    void deleteByRoleId(Long roleId);

    void batchInsert(@Param("roleId") Long roleId, @Param("list")List<Long> resourceIds);

    Set<String> queryResourceByRoleIds(@Param("list")Set<Long> roleSet);
}