package com.linkedin.interview.mastermind.api.exception;

public class FailedInitiationException extends RuntimeException {
	
	public FailedInitiationException(String msg) {
		super(msg);
	}
	
	int statusCode;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	
	
}
