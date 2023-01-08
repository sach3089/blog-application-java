package com.javaenthu.blog.app.blogapplication.responses;

import java.util.Date;

import com.javaenthu.blog.app.blogapplication.models.Post;

public class CommentResponse {
	
	private int id;
	private String content;
	private Post post;
	private int createdBy;
	private Date createdDate;
	private int updatedBy;
	private Date updatedDate;

}
