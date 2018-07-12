package com.sptwin.apchy.web.sys.service.impl;

import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.sys.mapper.OrderMapper;
import com.sptwin.apchy.web.sys.service.RoleService;
import com.sptwin.apchy.web.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Role> findRolesByUserId(Long id) {
        return orderMapper.findRolesByUserId(id);
    }
}
