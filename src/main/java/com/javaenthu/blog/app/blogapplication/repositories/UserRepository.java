package com.javaenthu.blog.app.blogapplication.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javaenthu.blog.app.blogapplication.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
	@Query("select u from User u where u.firstName like %:search% or u.lastName like %:search% or u.email like %:search%")
	Page<User> findByName(String search, Pageable page);

	User findByEmail(String email);
	
	

}
