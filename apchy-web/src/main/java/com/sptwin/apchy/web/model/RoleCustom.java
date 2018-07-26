package com.sptwin.apchy.web.model;

import com.sptwin.apchy.web.entity.Role;
import com.sptwin.apchy.web.entity.User;
import com.sptwin.spchy.model.enums.Available;

import java.util.List;

public class RoleCustom extends Role {
    private List<User> users;

    private Available availableEnum;


    public Available getAvailableEnum() {
        return availableEnum;
    }

    public void setAvailableEnum(Available availableEnum) {
        this.availableEnum = availableEnum;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
