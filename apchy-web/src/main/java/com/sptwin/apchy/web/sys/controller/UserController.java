package com.sptwin.apchy.web.sys.controller;

import com.alibaba.fastjson.JSON;
import com.sptwin.apchy.web.model.UserCustom;
import com.sptwin.apchy.web.sys.service.UserService;
import com.sptwin.spchy.model.common.Pagination;
import com.sptwin.spchy.model.common.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String userPage() {
        return "sys/UserList";
    }

    @GetMapping("/addEditPage")
    public String addEditPage() {
        return "sys/UserAddEdit";
    }

    /**
     * 分页查询
     * @param json
     * @param grid
     * @return
     */
    @RequestMapping(value = "/queryUser")
    public
    @ResponseBody
    Map queryUser(String json, Pagination grid){
        UserCustom userCustom = JSON.parseObject(json, UserCustom.class);
        if (null == userCustom) {
            userCustom = new UserCustom();
        }
        Map map = userService.queryUser(userCustom,grid);
        return map;
    }

    /**
     * 添加、编辑
     * @param data
     * @throws Exception
     */
    @RequestMapping("/insertOrEditUser")
    @ResponseBody
    public ResponseJson<Object> insertOrEditUser(String data) throws Exception {
        ResponseJson<Object> responseJson = new ResponseJson<>();
        UserCustom userCustom = JSON.parseObject(data, UserCustom.class);
        responseJson =  userService.insertOrEditUser(userCustom,responseJson);
        return responseJson;
    }

    /**
     * 编辑查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/editQuery")
    @ResponseBody
    public
    UserCustom editQuery(Long id) {
        return userService.editQuery(id);
    }

    /**
     * 删除
     * @param id
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public
    void deleteUser(Long id) {
        //List idList = Arrays.asList(ids.split(","));
        userService.deleteUser(id);
    }
}
