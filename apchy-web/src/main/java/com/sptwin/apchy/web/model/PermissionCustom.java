package com.sptwin.apchy.web.model;

import java.util.List;

public class PermissionCustom extends Permission {
    private List<PermissionCustom> children;

    public List<PermissionCustom> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionCustom> children) {
        this.children = children;
    }
}
