package com.sptwin.apchy.web.sys.pojo;

import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.entity.User;

import java.util.List;

public class RoleCustom extends Role {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
