package com.sptwin.apchy.web.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sptwin.apchy.web.entity.Resource;
import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.entity.User;
import com.sptwin.apchy.web.model.MenuLeft;
import com.sptwin.apchy.web.model.RoleCustom;
import com.sptwin.apchy.web.service.SessionService;
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
import java.util.Set;

@Controller
@RequestMapping("resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private SessionService sessionService;

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
        User user = sessionService.getUser();
        List<MenuLeft> list = resourceService.queryMenuByUserId(user.getId());
        return list;
    }

    /*public String readToString(String fileName) {
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
    }*/
    @RequestMapping(value = "/queryResource")
    public
    @ResponseBody
    List queryResource(){
        List list = resourceService.queryResource();
        return list;
    }
    /**
     * 添加、编辑
     * @param data
     * @throws Exception
     */
    @RequestMapping("/insertOrEdit")
    @ResponseBody
    public
    void insertOrEdit(String data) throws Exception {
        Resource resource = JSON.parseObject(data, Resource.class);
        resourceService.insertOrEdit(resource);
    }
    /**
     * 编辑查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/editQuery")
    @ResponseBody
    public
    Resource editQuery(Long id) {
        return resourceService.editQuery(id);
    }
    /**
     * 删除
     * @param id
     */
    @RequestMapping("/deleteOne")
    @ResponseBody
    public
    void deleteOne(Long id) {
        //List idList = Arrays.asList(ids.split(","));
        resourceService.deleteOne(id);
    }
}
