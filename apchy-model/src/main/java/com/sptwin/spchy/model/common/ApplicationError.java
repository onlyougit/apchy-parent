package com.sptwin.spchy.model.common;

public enum ApplicationError {

	FAILED("失败", "0"),
	SUCCESS("成功", "1"),
	PARAMETER_ERROR("参数错误", "2"),
	USERNAME_PW_ERROR("用户名或密码错误", "1001"),
	AUTHCODE_ERROR("验证码错误", "1002"),

	;
	private String message;
	private String code;

	private ApplicationError(String message, String code) {
		this.message = message;
		this.code = code;
	}

	public static ApplicationError get(String code) {
		for (ApplicationError ae : ApplicationError.values()) {
			if (ae.getCode().equals(code)) {
				return ae;
			}
		}
		return null;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
