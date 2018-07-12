package com.sptwin.spchy.model.common;

import java.io.Serializable;

public class ResponseJson<T extends Object> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6180990551947777230L;
	private String code;// 返回码
	private String msg;// 消息通知
	private T data;// 数据


	public ResponseJson() {
		super();
		this.code = ApplicationError.SUCCESS.getCode();
		this.msg = ApplicationError.SUCCESS.getMessage();
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	

}
