package com.javaenthu.blog.app.blogapplication.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.javaenthu.blog.app.blogapplication.models.Comment;
import com.javaenthu.blog.app.blogapplication.models.Post;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

	Page<Comment> findByPost(Post post, Pageable page);

}
