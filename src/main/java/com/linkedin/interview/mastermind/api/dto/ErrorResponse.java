package com.linkedin.interview.mastermind.api.dto;

public class ErrorResponse {
	
	private int code;
	
	private String msg;

	public int getCode() {
		return code; //tcomment
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
