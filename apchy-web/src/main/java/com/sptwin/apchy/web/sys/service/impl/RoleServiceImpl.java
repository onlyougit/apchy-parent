package com.sptwin.apchy.web.sys.service.impl;

import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.sys.mapper.RoleCustomMapper;
import com.sptwin.apchy.web.sys.mapper.RoleMapper;
import com.sptwin.apchy.web.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleCustomMapper roleCustomMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRolesByUserId(Long id) {
        return roleCustomMapper.findRolesByUserId(id);
    }

    @Override
    public void insertRole(Role role) {
        roleMapper.insertSelective(role);
    }

}
