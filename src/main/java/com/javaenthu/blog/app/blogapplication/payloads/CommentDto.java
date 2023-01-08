package com.javaenthu.blog.app.blogapplication.payloads;

import lombok.Data;

@Data
public class CommentDto {
	
	private int id;
	private int postId;
	private String content;

}
