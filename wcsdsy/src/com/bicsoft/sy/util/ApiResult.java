package com.bicsoft.sy.util;

public class ApiResult {
	private int status;
	private String msg;
	private Object data;

	public ApiResult() {
	}

	public ApiResult(boolean success) {
		this.status = success ? 0 : 1;
	}

	public ApiResult(boolean success, String msg) {
		this(success);
		this.msg = msg;
	}

	public int getStatus() {
		return this.status;
	}

	public String getMsg() {
		return this.msg;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
