package com.sptwin.spchy.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum IconCls {
    DESKTOP("fa fa-desktop", "fa fa-desktop"),
    MODX("fa fa-modx", "fa fa-modx"),
    WINDOW_RESTORE("fa fa-window-restore", "fa fa-window-restore"),
    USER("fa fa-user", "fa fa-user"),
    SHARE_ALT("fa fa-share-al", "fa fa-share-alt"),
    FEED("fa-feed", "fa-feed"),

    ;

    /* 编码 */
    private String code;

    /* 描述 */
    private String text;

    IconCls(String code, String text) {
        this.code = code;
        this.text = text;
    }
    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
}
