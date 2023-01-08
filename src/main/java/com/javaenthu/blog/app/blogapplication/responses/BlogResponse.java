package com.javaenthu.blog.app.blogapplication.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponse {
	
	private String responseMessage;
	private String responseCode;
	private Object responseObject;

}
