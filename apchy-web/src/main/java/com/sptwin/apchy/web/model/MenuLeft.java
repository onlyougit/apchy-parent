package com.sptwin.apchy.web.model;

import java.util.List;

public class MenuLeft {
    private Long id;

    private String iconCls;

    private String text;

    private String url;

    private Long parentId;

    private List<MenuLeft> children;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuLeft> getChildren() {
        return children;
    }

    public void setChildren(List<MenuLeft> children) {
        this.children = children;
    }
}
