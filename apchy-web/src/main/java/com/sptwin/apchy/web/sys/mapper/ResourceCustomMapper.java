package com.sptwin.apchy.web.sys.mapper;


import com.sptwin.apchy.web.model.MenuLeft;
import com.sptwin.apchy.web.model.ResourceCustom;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ResourceCustomMapper {
    List<ResourceCustom> queryResource();

    //List<MenuLeft> queryResourceByParentIds(@Param("resourceType")Integer resourceType, @Param("list")Set<Long> parentIds);
    List<MenuLeft> queryResourceByParentIds(@Param("list")Set<Long> parentIds);

    MenuLeft queryById(Long pid);
}