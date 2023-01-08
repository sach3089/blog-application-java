package com.javaenthu.blog.app.blogapplication.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javaenthu.blog.app.blogapplication.models.Category;
import com.javaenthu.blog.app.blogapplication.models.Post;
import com.javaenthu.blog.app.blogapplication.models.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
    
	@Query("select p from Post p where p.title like %:search% ")
	Page<Post> findByTitle(String search, Pageable page);
	
	@Query("select p from Post p where p.user= :user")
	Page<Post> findAllPostByUser(User user, Pageable page);
	
	@Query("select p from Post p where p.user= :user and p.title like %:search% ")
	Page<Post> findByTitleAndUser(User user, String search,Pageable page);
    
	@Query("select p from Post p where p.category= :category")
	Page<Post> findAllPostByCategory(Category category, Pageable page);
    
	@Query("select p from Post p where p.category= :category and p.title like %:search% ")
	Page<Post> findByTitleAndCategory(Category category, String search, Pageable page);
	
	

}
