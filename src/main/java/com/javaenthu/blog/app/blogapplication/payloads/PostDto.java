package com.javaenthu.blog.app.blogapplication.payloads;

import com.javaenthu.blog.app.blogapplication.models.Category;
import com.javaenthu.blog.app.blogapplication.models.User;

import lombok.Data;

@Data
public class PostDto {
	
	private int id;
	private String title;
	private String content;
	private String imageName;
	private int userId;
	private int categoryId;

}
