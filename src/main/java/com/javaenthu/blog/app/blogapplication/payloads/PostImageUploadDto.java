package com.javaenthu.blog.app.blogapplication.payloads;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PostImageUploadDto {
	
	private MultipartFile image;
	private int userId;
	private int postId;

}
