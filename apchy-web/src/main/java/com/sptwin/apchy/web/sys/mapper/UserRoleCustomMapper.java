package com.sptwin.apchy.web.sys.mapper;

import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserRoleCustomMapper {
    @Select("select r.id,r.role_name roleName,r.description,r.available from t_sys_user_role ur,t_sys_role r where ur.role_id = r.id and ur.user_id = #{value}")
    List<Role> getRoleByUserId(Long id);
    @Select("select u.id,u.user_name userName,u.real_name realName from t_sys_user_role ur,t_sys_user u where ur.role_id = u.id and ur.role_id = #{value}")
    List<User> getUserByRoleId(Long id);

    @Delete("delete from t_sys_user_role where user_id = #{id,jdbcType=INTEGER}")
    void batchDeleteByUserId(Long id);

    void batchInsert(@Param("id")Long id, @Param("list")List idList);
}
