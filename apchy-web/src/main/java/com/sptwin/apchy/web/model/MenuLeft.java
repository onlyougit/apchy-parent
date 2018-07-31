package com.sptwin.apchy.web.model;

import java.util.List;

public class MenuLeft {
    private String id;

    private String iconCls;

    private String text;

    private String url;

    private Long parentId;

    private Integer priority;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    private List<MenuLeft> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
