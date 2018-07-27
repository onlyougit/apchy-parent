package com.sptwin.apchy.web.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.sptwin.apchy.web.entity.Resource;
import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.model.Functions;
import com.sptwin.apchy.web.model.Permission;
import com.sptwin.apchy.web.model.ResourceCustom;
import com.sptwin.apchy.web.model.RoleCustom;
import com.sptwin.apchy.web.service.SessionService;
import com.sptwin.apchy.web.sys.mapper.ResourceCustomMapper;
import com.sptwin.apchy.web.sys.mapper.RoleCustomMapper;
import com.sptwin.apchy.web.sys.mapper.RoleMapper;
import com.sptwin.apchy.web.sys.mapper.RoleResourceCustomMapper;
import com.sptwin.apchy.web.sys.service.RoleService;
import com.sptwin.spchy.model.common.PageBean;
import com.sptwin.spchy.model.common.Pagination;
import com.sptwin.spchy.model.enums.Available;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleCustomMapper roleCustomMapper;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private ResourceCustomMapper resourceCustomMapper;
    @Autowired
    private RoleResourceCustomMapper roleResourceCustomMapper;


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


    @Override
    public List queryPermission(Long roleId) {
        //查询该角色拥有的权限
        List<Long> resourceIds = roleResourceCustomMapper.queryResourceByRoleId(roleId);
        //查询所有资源
        List<ResourceCustom> resourceCustomList = resourceCustomMapper.queryResource();
        List<ResourceCustom> buttonResourceList = resourceCustomList.stream().filter(w->w.getResourceTypeEnum().ordinal()==1).collect(Collectors.toList());
        List<ResourceCustom> menuResourceList = resourceCustomList.stream().filter(w->w.getResourceTypeEnum().ordinal()==0).collect(Collectors.toList());
        List<Permission> result = new ArrayList<>();
        menuResourceList.forEach(w->{
            Permission permission = new Permission();
            permission.setId(w.getId());
            permission.setName(w.getResourceName());
            permission.setPid(w.getParentId());
            result.add(permission);
        });
        Map<Long, List<ResourceCustom>> map = buttonResourceList
                .stream()
                .collect(Collectors.groupingBy(ResourceCustom::getParentId));
        Iterator<Map.Entry<Long, List<ResourceCustom>>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            List<Functions> functionsList = new ArrayList<>();
            Map.Entry<Long, List<ResourceCustom>> entry = entries.next();
            List<ResourceCustom> list = entry.getValue();
            list.forEach(w->{
                Functions functions = new Functions();
                functions.setId(w.getId());
                functions.setName(w.getResourceName());
                if(resourceIds.contains(w.getId())){
                    functions.setChecked(true);
                }else{
                    functions.setChecked(false);
                }
                functionsList.add(functions);
            });
            Long key = entry.getKey();
            result.forEach(w->{
                if(w.getId().equals(key)){
                    w.setFunctions(functionsList);
                }
            });
        }
        return result;
    }
}
