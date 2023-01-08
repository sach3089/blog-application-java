package com.javaenthu.blog.app.blogapplication.responses;

import java.util.Date;

import com.javaenthu.blog.app.blogapplication.models.Category;
import com.javaenthu.blog.app.blogapplication.models.User;

import lombok.Data;

@Data
public class PostResponse {
	
	private int id;
	private String title;
	private String content;
	private String imageName;
	private User user;
	private Category category;
	private int createdBy;
	private Date createdDate;
	private int updatedBy;
	private Date updatedDate;

}
