package com.javaenthu.blog.app.blogapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaenthu.blog.app.blogapplication.payloads.CommentDto;
import com.javaenthu.blog.app.blogapplication.payloads.PostDto;
import com.javaenthu.blog.app.blogapplication.responses.BlogResponse;
import com.javaenthu.blog.app.blogapplication.services.CommentService;
import com.javaenthu.blog.app.blogapplication.utilities.Constants;

@RestController
@RequestMapping(value = "/api/v1/comment")
public class CommentController extends BaseController{
	
	@Autowired
	private CommentService commentService;
	
	
	@GetMapping("/getAllComments")
	public ResponseEntity<BlogResponse> getAllPosts(@RequestParam(defaultValue = "0") int startIndex, @RequestParam(defaultValue = "0") int pageSize,
	      @RequestParam(defaultValue = "id") String sortBy, @RequestParam(required = false) String search){
		Object response = commentService.getAllComment(startIndex, pageSize, search, sortBy);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	
	@GetMapping("/getAllCommentByPost/{postId}")
	public ResponseEntity<BlogResponse> getAllPostByUser(@RequestParam(defaultValue = "0") int startIndex, @RequestParam(defaultValue = "0") int pageSize,
		      @RequestParam(defaultValue = "id") String sortBy, @RequestParam(required = false) String search,@PathVariable("postId") int postId){
		Object response = commentService.getAllCommentByPost(startIndex,pageSize,postId, search,sortBy );
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	
	@PostMapping("/createNewComment")
	public ResponseEntity<BlogResponse> createNewPost(@RequestBody CommentDto dto){
		Object response = commentService.createNewComment(dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.CREATED.toString(), response));
	}
	
	@DeleteMapping("/deleteComment/{id}")
	public ResponseEntity<BlogResponse> deleteCommentById(@PathVariable("id") int id){
		commentService.deleteComment(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), "Category Deleted"));
	}
	
	
	

}
