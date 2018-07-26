package com.sptwin.apchy.web.controller;

import com.sptwin.spchy.model.enums.IconCls;
import com.sptwin.spchy.model.enums.ResourceType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("base")
public class BaseController {

    @GetMapping("/resourceType")
    public
    List resourceType() {
        List list = new ArrayList<>();
        for (ResourceType e : ResourceType.values()) {
            list.add(e);
        }
        return list;
    }

    @GetMapping("/iconCls")
    public
    List iconCls() {
        List list = new ArrayList<>();
        for (IconCls e : IconCls.values()) {
            list.add(e);
        }
        return list;
    }
}
