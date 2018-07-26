package com.sptwin.spchy.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResourceType {
    MENU(0, "菜单"),
    BUTTON(1, "按钮"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    ResourceType(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
    public Integer getCode() {
        return code;
    }


    public void setCode(Integer code) {
        this.code = code;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
}
