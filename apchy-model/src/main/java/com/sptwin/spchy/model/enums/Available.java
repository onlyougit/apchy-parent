package com.sptwin.spchy.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Available {
    INVALID(0, "无效"),
    EFFECTIVE(1, "有效"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    Available(Integer code, String text) {
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
