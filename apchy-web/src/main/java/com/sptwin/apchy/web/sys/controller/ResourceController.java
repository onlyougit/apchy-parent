package com.sptwin.apchy.web.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sptwin.apchy.web.model.MenuLeft;
import com.sptwin.apchy.web.model.RoleCustom;
import com.sptwin.apchy.web.sys.service.ResourceService;
import com.sptwin.spchy.model.common.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping
    public String resourcePage() {
        return "sys/ResourceList";
    }
    @GetMapping("/addEditPage")
    public String addEditPage() {
        return "sys/ResourceAddEdit";
    }

    @RequestMapping("/getLeftMenu")
    @ResponseBody
    public List<MenuLeft> getLeftMenu(){
        String jsonString = readToString("menu.txt");
        List<MenuLeft> list = JSONArray.parseArray(jsonString,MenuLeft.class);
        return list;
    }
    public String readToString(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(fileName);
        String encoding = "UTF-8";
        File file = new File(url.getFile());
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping(value = "/queryResource")
    public
    @ResponseBody
    List queryResource(){
        List list = resourceService.queryResource();
        return list;
    }
}
