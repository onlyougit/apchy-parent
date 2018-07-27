package com.sptwin.apchy.web.sys.service;

import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.model.RoleCustom;
import com.sptwin.spchy.model.common.Pagination;

import java.util.List;
import java.util.Map;

public interface RoleService {
    Map queryRole(RoleCustom roleCustom, Pagination grid);

    void insertOrEditRole(Role role);

    Role editQuery(Long id);

    void deleteRole(Long id);

    List queryPermission(Long roleId);
}
