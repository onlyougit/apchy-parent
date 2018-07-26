package com.sptwin.apchy.web.sys.service.impl;

import com.sptwin.apchy.web.sys.mapper.ResourceCustomMapper;
import com.sptwin.apchy.web.sys.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceCustomMapper resourceCustomMapper;

    @Override
    public List queryResource() {
        return resourceCustomMapper.queryResource();
    }
}
