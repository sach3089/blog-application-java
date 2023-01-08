package com.javaenthu.blog.app.blogapplication.utilities;

import lombok.Data;

@Data
public class Constants {
	
	public static final String SUCCESS = "Success";
	public static final String FAILED= "Failed";
	
	////user constants
	public static final String USER_NOT_FOUND ="User Not Found";
	public static final String USER_EMAIL_ALREADY_EXIST ="User Email Already Exist";
	
	///category constants
	public static final String CATEGORY_NOT_FOUND ="User Not Found";
	public static final String CATEGORY_NAME_ALREADY_EXIST ="Category Name Already Exist";
	
	//post constant
	public static final String POST_NOT_FOUND = "Post Not Found";
	public static final String POST_NAME_ALREADY_EXIST = "Post Name Already Exist";
	
	//comment constant
	public static final String COMMENT_NOT_FOUND ="Comment Not Found";
	
	//role constant
	public static final int ADMIN_USER = 1;
	public static final int CLIENT_USER = 2;

}
