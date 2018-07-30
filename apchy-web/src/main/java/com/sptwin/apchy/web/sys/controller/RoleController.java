package com.sptwin.apchy.web.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.model.PermissionCustom;
import com.sptwin.apchy.web.model.RoleCustom;
import com.sptwin.apchy.web.sys.service.RoleService;
import com.sptwin.spchy.model.common.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String rolePage() {
        return "sys/RoleList";
    }
    @GetMapping("/addEditPage")
    public String addEditPage() {
        return "sys/RoleAddEdit";
    }
    @GetMapping("/toUserPage")
    public String toUserPage() {
        return "sys/RoleToUser";
    }
    @GetMapping("/roleResourcePage")
    public String roleResourcePage() {
        return "sys/RoleResource";
    }

    @RequestMapping(value = "/queryPermission")
    public
    @ResponseBody
    List queryPermission(Long roleId){
        List list = roleService.queryPermission(roleId);
        return list;
    }
    @RequestMapping("/savePermission")
    @ResponseBody
    public
    void savePermission(Long roleId , String data) throws Exception {
        List<PermissionCustom> permissionCustom = JSON.parseArray(data,PermissionCustom.class);
        roleService.savePermission(roleId,permissionCustom);
    }
    /**
     * 分页查询
     * @param json
     * @param grid
     * @return
     */
    @RequestMapping(value = "/queryRole")
    public
    @ResponseBody
    Map queryRole(String json, Pagination grid){
        RoleCustom roleCustom = JSON.parseObject(json, RoleCustom.class);
        if (null == roleCustom) {
            roleCustom = new RoleCustom();
        }
        Map map = roleService.queryRole(roleCustom,grid);
        return map;
    }

    /**
     * 添加、编辑
     * @param data
     * @throws Exception
     */
    @RequestMapping("/insertOrEditRole")
    @ResponseBody
    public
    void insertOrEditRole(String data) throws Exception {
        Role role = JSON.parseObject(data, Role.class);
        roleService.insertOrEditRole(role);
    }

    /**
     * 编辑查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/editQuery")
    @ResponseBody
    public
    Role editQuery(Long id) {
        return roleService.editQuery(id);
    }

    /**
     * 删除
     * @param id
     */
    @RequestMapping("/deleteRole")
    @ResponseBody
    public
    void deleteRole(Long id) {
        //List idList = Arrays.asList(ids.split(","));
        roleService.deleteRole(id);
    }
}
