package com.javaenthu.blog.app.blogapplication.exceptions;

public class ApiException extends RuntimeException {
	
	private String message;
	private String errorCode;
	public ApiException(String message, String errorCode) {
		super();
		this.message = message;
		this.errorCode = errorCode;
	}
	
	

}
