package com.javaenthu.blog.app.blogapplication.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaenthu.blog.app.blogapplication.exceptions.ApiException;
import com.javaenthu.blog.app.blogapplication.models.User;
import com.javaenthu.blog.app.blogapplication.payloads.JwtAuthRequest;
import com.javaenthu.blog.app.blogapplication.payloads.UserDto;
import com.javaenthu.blog.app.blogapplication.responses.BlogResponse;
import com.javaenthu.blog.app.blogapplication.responses.JwtAuthResponse;
import com.javaenthu.blog.app.blogapplication.secuirty.JwtTokenHelper;
import com.javaenthu.blog.app.blogapplication.services.UserService;
import com.javaenthu.blog.app.blogapplication.utilities.BlogErrors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
		
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails =this.userDetailService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(modelMapper.map((User)userDetails, UserDto.class));
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}
	
	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			authenticationManager.authenticate(usernamePasswordAuthToken);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ApiException("Invalid Details",""+BlogErrors.INVALID_CREDDETIAL); 
		}
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<Object>  registerNewUser(@RequestBody UserDto dto){
		
		Object response = userService.registerNewUser(dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(response);
	}

}
