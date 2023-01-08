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
import org.springframework.stereotype.Service;

import com.javaenthu.blog.app.blogapplication.exceptions.NotFoundException;
import com.javaenthu.blog.app.blogapplication.models.Comment;
import com.javaenthu.blog.app.blogapplication.models.Post;
import com.javaenthu.blog.app.blogapplication.payloads.CommentDto;
import com.javaenthu.blog.app.blogapplication.repositories.CommentRepository;
import com.javaenthu.blog.app.blogapplication.repositories.PostRepository;
import com.javaenthu.blog.app.blogapplication.responses.CommentResponse;
import com.javaenthu.blog.app.blogapplication.utilities.BlogErrors;
import com.javaenthu.blog.app.blogapplication.utilities.Constants;

@Service
public class CommentService {
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Object getAllComment(int startIndex, int pageSize, String search, String sortBy) {
		Pageable page;
		Page<Comment> pageResult;
		if (pageSize == 0) {
			if (sortBy == "title") {
				page = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, sortBy));
			} else {
				page = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, sortBy));
			}
		} else {
			int pageNumber = (startIndex + (pageSize - 1)) / pageSize;
			page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
		}
		pageResult = commentRepo.findAll(page);
		Map<String, Object> response = new HashMap<String, Object>();
		if (pageResult == null || pageResult.getContent().isEmpty()) {
			response.put("totalElements", 0);
			response.put("totalPages", 0);
			response.put("comments", new ArrayList<>());
		} else {
			response.put("totalElements", pageResult.getTotalElements());
			response.put("totalPages", pageResult.getTotalPages());
			response.put("comments", pageResult.getContent());
		}
		return response;
	}
	
	public Object getAllCommentByPost(int startIndex, int pageSize,int postId, String search, String sortBy) {
		Pageable page;
		Page<Comment> pageResult;
		if (pageSize == 0) {
			if (sortBy == "title") {
				page = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, sortBy));
			} else {
				page = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, sortBy));
			}
		} else {
			int pageNumber = (startIndex + (pageSize - 1)) / pageSize;
			page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
		}
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new NotFoundException(Constants.POST_NOT_FOUND, "" + BlogErrors.POST_NOT_FOUND));
		pageResult = commentRepo.findByPost(post, page);
		Map<String, Object> response = new HashMap<String, Object>();
		if (pageResult == null || pageResult.getContent().isEmpty()) {
			response.put("totalElements", 0);
			response.put("totalPages", 0);
			response.put("comments", new ArrayList<>());
		} else {
			response.put("totalElements", pageResult.getTotalElements());
			response.put("totalPages", pageResult.getTotalPages());
			response.put("comments", pageResult.getContent());
		}
		return response;
	}
	
	
	
	public CommentResponse createNewComment(CommentDto dto) {
	  
		Post post = postRepo.findById(dto.getPostId())
				.orElseThrow(() -> new NotFoundException(Constants.POST_NOT_FOUND, "" + BlogErrors.POST_NOT_FOUND));
		Comment comment = new Comment();
		comment.setContent(dto.getContent());
		comment.setPost(post);
		//comment created data createdByUser v add krna h
		Comment savedComment= commentRepo.save(comment);
		CommentResponse response = modelMapper.map(savedComment, CommentResponse.class);
		return response;
	}
	
	public String deleteComment(int commen_id) {
		 
		Comment comment = commentRepo.findById(commen_id)
				.orElseThrow(() -> new NotFoundException(Constants.COMMENT_NOT_FOUND, "" + BlogErrors.COMMENT_NOT_FOUND));
		commentRepo.delete(comment);
		return "Comment deleted Successfully";
	}

}
