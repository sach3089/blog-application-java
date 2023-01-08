package com.javaenthu.blog.app.blogapplication.utilities;

import lombok.Data;

@Data
public class BlogErrors {
	
	
	//user errors
	public static final int USER_NOT_FOUND = 1000;
	public static final int USER_ALREADY_EXISTS = 1001;
	
	//CATEGORY ERRORS
	public static final int CATEGORY_NOT_FOUND=2000;
	public static final int CATEGORY_NAME_ALREADY_EXISTS=2001;
	
	//post errors
	public static final int POST_NOT_FOUND =3000;
	public static final int POST_NAME_ALREADY_EXISTS=3001;
	public static final int FILE_NOT_UPLOAD_EXCEPTION=3002;
	
	//comment errors
	public static final int COMMENT_NOT_FOUND=5000;
	
	//login errors
	public static final int INVALID_CREDDETIAL=4000;

}
