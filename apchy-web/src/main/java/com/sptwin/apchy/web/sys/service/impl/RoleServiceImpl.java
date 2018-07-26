package com.sptwin.apchy.web.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.model.RoleCustom;
import com.sptwin.apchy.web.service.SessionService;
import com.sptwin.apchy.web.sys.mapper.RoleCustomMapper;
import com.sptwin.apchy.web.sys.mapper.RoleMapper;
import com.sptwin.apchy.web.sys.service.RoleService;
import com.sptwin.spchy.model.common.PageBean;
import com.sptwin.spchy.model.common.Pagination;
import com.sptwin.spchy.model.enums.Available;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleCustomMapper roleCustomMapper;
    @Autowired
    private SessionService sessionService;


    @Override
    public Map queryRole(RoleCustom roleCustom, Pagination grid) {
        Map map = new HashMap();
        PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
        List<RoleCustom> roleCustomList = roleCustomMapper.queryRole(roleCustom);
        PageBean<RoleCustom> pb = new PageBean(roleCustomList);
        map.put("data", pb.getList());
        map.put("total", pb.getTotal());
        return map;
    }

    @Override
    public void insertOrEditRole(Role role) {
        Date date = new Date();
        if(null == role.getId()){
            User user = sessionService.getUser();
            role.setAvailable(Available.EFFECTIVE.ordinal());
            role.setGmtCreate(date);
            roleMapper.insertSelective(role);
        }else {
            role.setGmtModified(date);
            roleMapper.updateByPrimaryKeySelective(role);
        }
    }

    @Override
    public Role editQuery(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = new Role();
        role.setGmtModified(new Date());
        role.setId(id);
        role.setAvailable(Available.INVALID.ordinal());
        roleMapper.updateByPrimaryKeySelective(role);
    }
}
