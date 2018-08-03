package com.sptwin.apchy.user.controller;

import com.sptwin.apchy.user.service.LcnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LcnController {

    @Autowired
    private LcnService lcnService;

    @GetMapping("/lcn/add/{id}")
    public void testLcn(@PathVariable("id") Integer id){
        lcnService.testLcn(id);
    }
}
