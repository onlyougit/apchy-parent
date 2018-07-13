package com.sptwin.apchy.web.sys.service;

import com.sptwin.apchy.web.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findRolesByUserId(Long id);

    public void insertRole(Role role);
}
