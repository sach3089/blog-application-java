package com.javaenthu.blog.app.blogapplication.secuirty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.javaenthu.blog.app.blogapplication.models.User;
import com.javaenthu.blog.app.blogapplication.repositories.UserRepository;

@Service
public class CustomUserSecurity implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		return user;
	}
	
	

}
