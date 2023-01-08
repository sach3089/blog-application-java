package com.javaenthu.blog.app.blogapplication.responses;

import com.javaenthu.blog.app.blogapplication.payloads.UserDto;

import lombok.Data;

@Data
public class JwtAuthResponse {
	
	private String token;
	private UserDto user;
	

}
