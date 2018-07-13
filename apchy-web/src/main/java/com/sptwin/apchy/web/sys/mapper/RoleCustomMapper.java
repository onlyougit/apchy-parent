package com.sptwin.apchy.web.sys.mapper;

import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleCustomMapper {

    List<Role> findRolesByUserId(Long id);
}
