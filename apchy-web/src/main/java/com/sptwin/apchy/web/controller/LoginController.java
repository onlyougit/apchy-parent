package com.sptwin.apchy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("index")
    public String indexPage(){
        return "index";
    }

    @RequestMapping("main")
    public String mainPage(){
        return "main";
    }
}
