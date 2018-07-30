package com.sptwin.apchy.web.sys.service.impl;

import com.sptwin.apchy.web.entity.Resource;
import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.model.Functions;
import com.sptwin.apchy.web.model.MenuLeft;
import com.sptwin.apchy.web.model.MenuLeftCustom;
import com.sptwin.apchy.web.model.ResourceCustom;
import com.sptwin.apchy.web.sys.mapper.ResourceCustomMapper;
import com.sptwin.apchy.web.sys.mapper.ResourceMapper;
import com.sptwin.apchy.web.sys.mapper.RoleResourceCustomMapper;
import com.sptwin.apchy.web.sys.mapper.UserRoleCustomMapper;
import com.sptwin.apchy.web.sys.service.ResourceService;
import com.sptwin.spchy.model.enums.Available;
import com.sptwin.spchy.model.enums.ResourceType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceCustomMapper resourceCustomMapper;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleResourceCustomMapper roleResourceCustomMapper;
    @Autowired
    private UserRoleCustomMapper userRoleCustomMapper;

    @Override
    public List queryResource() {
        List<ResourceCustom> resourceCustomList = resourceCustomMapper.queryResource();
        return resourceCustomList;
    }

    @Override
    public void insertOrEdit(Resource resource) {
        Date date = new Date();
        if(null == resource.getId()){
            resource.setAvailable(Available.EFFECTIVE.ordinal());
            resource.setGmtCreate(date);
            resourceMapper.insertSelective(resource);
        }else {
            resource.setGmtModified(date);
            resourceMapper.updateByPrimaryKeySelective(resource);
        }
    }

    @Override
    public Resource editQuery(Long id) {
        return resourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteOne(Long id) {
        //先删角色资源表，再删资源表
        roleResourceCustomMapper.deleteByResourceId(id);
        resourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<MenuLeft> queryMenuByUserId(Long id) {
        List<MenuLeft> result = new ArrayList<>();
        List<Role> roles=this.userRoleCustomMapper.getRoleByUserId(id);
        Set<Resource> resourceSet = roleResourceCustomMapper.queryResourceByRoleIds(roles.stream().map(w->w.getId()).collect(Collectors.toSet()));
        Set<Long> parentIds = resourceSet.stream().map(w->w.getParentId()).distinct().collect(Collectors.toSet());
        List<MenuLeftCustom> menuLefts = resourceCustomMapper.queryResourceByParentIds(parentIds);
        Map<Long, List<MenuLeftCustom>> map = menuLefts
                .stream()
                .collect(Collectors.groupingBy(MenuLeftCustom::getParentId));
        Iterator<Map.Entry<Long, List<MenuLeftCustom>>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Long, List<MenuLeftCustom>> entry = entries.next();
            List<MenuLeftCustom> list = entry.getValue();
            Long pid = entry.getKey();
            MenuLeft menuLeft = resourceCustomMapper.queryById(pid);
            List<MenuLeft> menuLeftList = new ArrayList();
            list.forEach(y->{
                MenuLeft menuLeft1 = new MenuLeft();
                BeanUtils.copyProperties(y,menuLeft1);
                menuLeftList.add(menuLeft1);
            });
            menuLeft.setChildren(menuLeftList);
            result.add(menuLeft);

        }
        return result;
    }
}
