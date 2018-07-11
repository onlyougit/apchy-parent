package com.sptwin.apchy.web.sys.pojo;

import java.util.List;

public class MenuLeft {
    private String id;

    private String iconCls;

    private String text;

    private String url;

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
