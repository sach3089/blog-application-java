package com.javaenthu.blog.app.blogapplication.responses;

import java.util.Set;

import com.javaenthu.blog.app.blogapplication.payloads.RoleDto;

import lombok.Data;

@Data
public class UserResponse {
	
	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String password;
	private String desciption;
	private String profession;
	private  Set<RoleDto> roles;

}
