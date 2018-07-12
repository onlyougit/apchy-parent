package com.sptwin.spchy.model.enums;

public enum RoleStatus {
    INVALID(0, "无效"),
    EFFECTIVE(1, "有效"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    RoleStatus(Integer code, String text) {
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
