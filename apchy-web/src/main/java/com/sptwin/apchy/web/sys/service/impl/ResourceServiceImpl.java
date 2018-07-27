package com.sptwin.apchy.web.sys.service.impl;

import com.sptwin.apchy.web.entity.Resource;
import com.sptwin.apchy.web.model.ResourceCustom;
import com.sptwin.apchy.web.sys.mapper.ResourceCustomMapper;
import com.sptwin.apchy.web.sys.mapper.ResourceMapper;
import com.sptwin.apchy.web.sys.mapper.RoleResourceCustomMapper;
import com.sptwin.apchy.web.sys.service.ResourceService;
import com.sptwin.spchy.model.enums.Available;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceCustomMapper resourceCustomMapper;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleResourceCustomMapper roleResourceCustomMapper;

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
}
