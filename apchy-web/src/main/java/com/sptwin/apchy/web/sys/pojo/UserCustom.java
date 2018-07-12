package com.sptwin.apchy.web.sys.pojo;

import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.entity.User;

import java.util.List;

public class UserCustom extends User {

    private String authCode;

    private List<Role> roleList;

    private String roles;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

}
