package com.javaenthu.blog.app.blogapplication.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javaenthu.blog.app.blogapplication.exceptions.NotFoundException;
import com.javaenthu.blog.app.blogapplication.exceptions.ResourceExistException;
import com.javaenthu.blog.app.blogapplication.models.Role;
import com.javaenthu.blog.app.blogapplication.models.User;
import com.javaenthu.blog.app.blogapplication.payloads.UserDto;
import com.javaenthu.blog.app.blogapplication.repositories.RoleRepository;
import com.javaenthu.blog.app.blogapplication.repositories.UserRepository;
import com.javaenthu.blog.app.blogapplication.responses.UserResponse;
import com.javaenthu.blog.app.blogapplication.utilities.BlogErrors;
import com.javaenthu.blog.app.blogapplication.utilities.Constants;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepo;
	
	
	public Object getAllUsers(int startIndex, int pageSize, String search, String sortBy) {
		Pageable page;
		Page<User> pageResult;
		if(pageSize==0) {
			if(sortBy=="name") {
				page = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, sortBy));
			}else {
				page = PageRequest.of(pageSize, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
			}
		}else {
			int pageNumber = (startIndex + (pageSize-1))/pageSize;
			page = PageRequest.of(pageNumber, pageSize,Sort.by(Sort.Direction.DESC, sortBy));
		}
		if(search==null) {
			pageResult = userRepo.findAll(page);
		}else {
			pageResult = userRepo.findByName(search, page);
		}
		Map<String, Object> response = new HashMap<String, Object>();
		if(pageResult==null || pageResult.getContent().isEmpty()) {
			response.put("totalElements", 0);
			response.put("totalPages", 0);
			response.put("users", new ArrayList<>());
		}else {
			response.put("totalElements", pageResult.getTotalElements());
			response.put("totalPages", pageResult.getTotalPages());
			response.put("users", pageResult.getContent());
		}
		return response;
	}


	public Object getUserById(int id) {
		
		User user = userRepo.findById(id).orElseThrow(() ->
		        new NotFoundException(Constants.USER_NOT_FOUND,""+  BlogErrors.USER_NOT_FOUND));
		UserResponse response = modelMapper.map(user, UserResponse.class);
		return response;
		
	}

	public Object createNewUser(UserDto dto) {
		User userExist = userRepo.findByEmail(dto.getEmail());
		if(userExist!=null) {
			throw new ResourceExistException(Constants.USER_EMAIL_ALREADY_EXIST, "" + BlogErrors.USER_ALREADY_EXISTS);
		}
		User user = modelMapper.map(dto, User.class);
		User savedUser = userRepo.save(user);
		UserResponse response = modelMapper.map(savedUser, UserResponse.class);
		return response;
	}


	public Object updateUser(UserDto dto) {
		User user = userRepo.findById(dto.getId()).orElseThrow(() ->
                 new NotFoundException(Constants.USER_NOT_FOUND,""+  BlogErrors.USER_NOT_FOUND));
		user.setFirstName(dto.getFirstName());
		user.setMiddleName(dto.getMiddleName());
		user.setLastName(dto.getLastName());
		user.setDescription(dto.getDescription());
		user.setProfession(dto.getProfession());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		User updatedUser = userRepo.save(user);
		UserResponse response = modelMapper.map(updatedUser, UserResponse.class);
		return response;
	}


	public void deleteUserById(int id) {
		User user = userRepo.findById(id).orElseThrow(() ->
             new NotFoundException(Constants.USER_NOT_FOUND,""+  BlogErrors.USER_NOT_FOUND));
		userRepo.delete(user);
		return;
	}
	
	public Object registerNewUser(UserDto userDto) {
	    
		User user = modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role = roleRepo.findById(Constants.CLIENT_USER).get();
		user.getRole().add(role);
		User newuser = userRepo.save(user);
		UserResponse response = modelMapper.map(newuser, UserResponse.class);
		return response;
	}

}
