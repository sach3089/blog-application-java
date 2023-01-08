package com.javaenthu.blog.app.blogapplication.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
	
	private String username;
	private String password;

}
