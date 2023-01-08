package com.javaenthu.blog.app.blogapplication.payloads;

import lombok.Data;

@Data
public class CategoryDto {
	
	private int id;
	private String categoryName;
	private String categoryDescription;

}
