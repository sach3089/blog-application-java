package com.javaenthu.blog.app.blogapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaenthu.blog.app.blogapplication.payloads.UserDto;
import com.javaenthu.blog.app.blogapplication.responses.BlogResponse;
import com.javaenthu.blog.app.blogapplication.services.UserService;
import com.javaenthu.blog.app.blogapplication.utilities.Constants;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userServ;
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<BlogResponse> getAllUsers(@RequestParam(defaultValue = "0") int startIndex, @RequestParam(defaultValue = "0") int pageSize,
	      @RequestParam(defaultValue = "id") String sortBy, @RequestParam(required = false) String search){
		Object response = userServ.getAllUsers(startIndex, pageSize, search, sortBy);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	@GetMapping("/getUserById/{id}")
	public ResponseEntity<BlogResponse> getUserById(@PathVariable("id") int id){
		Object response = userServ.getUserById(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	
	@PostMapping("/createNewUser")
	public ResponseEntity<BlogResponse> createNewUser(@RequestBody UserDto dto){
		Object response = userServ.createNewUser(dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.CREATED.toString(), response));
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<BlogResponse> updateUser(@RequestBody UserDto dto){
		Object response = userServ.updateUser(dto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteUserById/{id}")
	public ResponseEntity<BlogResponse> deleteUserById(@PathVariable("id") int id){
		userServ.deleteUserById(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), "User Deleted"));
	}

}
