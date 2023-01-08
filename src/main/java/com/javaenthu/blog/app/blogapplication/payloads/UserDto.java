package com.javaenthu.blog.app.blogapplication.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class UserDto {
	
	private int id;
	
	@NotNull
	@Size(min=3, max = 15, message = "First Name must be of atleast 3 and max 15 characters!!")
	private String firstName;
	private String middleName;
	
	@NotNull
	@Size(min = 3, max = 15, message = "Last Name Must be of Atleast 3 and max 15 Characters!!")
	private String lastName;
	
	@NotNull
	@Email(message = "Email Address is not valid !!")
	@NotEmpty(message = "Email is Required")
	private String email;
	
	@NotEmpty
	@Size(min=3, max=10,message = "password must be min of 3 chars and max of 10 chars")
	private String password;
	
	@NotNull
	private String profession;
	
	
	private String description;
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password=password;
	}

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
