package com.sptwin.apchy.web.sys.service;

import com.sptwin.apchy.web.entity.Resource;
import com.sptwin.apchy.web.model.MenuLeft;
import java.util.List;

public interface ResourceService {
    List queryResource();

    void insertOrEdit(Resource resource);

    Resource editQuery(Long id);

    void deleteOne(Long id);

    List<MenuLeft> queryMenuByUserId(Long id);
}
