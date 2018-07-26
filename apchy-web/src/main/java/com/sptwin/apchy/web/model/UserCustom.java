package com.sptwin.apchy.web.model;

import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.entity.User;
import com.sptwin.spchy.model.enums.UserLockStatus;
import org.springframework.util.StringUtils;

import java.util.List;

public class UserCustom extends User {

    private String authCode;

    private List<Role> roleList;

    private String roleNames;

    private String roleIds;

    private UserLockStatus userLockStatusEnum;

    //新密码
    private String userNewPw;
    //密码确认
    private String userNewPwConfirm;

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public UserLockStatus getUserLockStatusEnum() {
        return userLockStatusEnum;
    }

    public void setUserLockStatusEnum(UserLockStatus userLockStatusEnum) {
        this.userLockStatusEnum = userLockStatusEnum;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

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

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

}
