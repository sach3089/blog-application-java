package com.javaenthu.blog.app.blogapplication.controllers;

import com.javaenthu.blog.app.blogapplication.responses.BlogResponse;

public class BaseController {
	
	public BlogResponse buildBlogResponse(String message, String statusCode, Object obj) {
		
		BlogResponse response = new BlogResponse();
		response.setResponseMessage(message);
		response.setResponseCode(statusCode);
		response.setResponseObject(obj);
		return response;
	}

}
