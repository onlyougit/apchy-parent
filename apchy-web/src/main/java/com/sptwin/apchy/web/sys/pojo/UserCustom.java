package com.sptwin.apchy.web.sys.pojo;

import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.entity.User;

import java.util.List;

public class UserCustom extends User {

    private String authCode;

    private List<Role> roleList;

    private String roles;

    //新密码
    private String userNewPw;
    //密码确认
    private String userNewPwConfirm;

    public String getUserNewPw() {
        return userNewPw;
    }

    public void setUserNewPw(String userNewPw) {
        this.userNewPw = userNewPw;
    }

    public String getUserNewPwConfirm() {
        return userNewPwConfirm;
    }

    public void setUserNewPwConfirm(String userNewPwConfirm) {
        this.userNewPwConfirm = userNewPwConfirm;
    }

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
