package com.javaenthu.blog.app.blogapplication.responses;

import lombok.Data;

@Data
public class CategoryResponse {
	
	private int id;
	private String categoryName;
	private String categoryDescription;

}
