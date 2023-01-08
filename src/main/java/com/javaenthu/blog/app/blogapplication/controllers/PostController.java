package com.javaenthu.blog.app.blogapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.javaenthu.blog.app.blogapplication.payloads.CategoryDto;
import com.javaenthu.blog.app.blogapplication.payloads.PostDto;
import com.javaenthu.blog.app.blogapplication.payloads.PostImageUploadDto;
import com.javaenthu.blog.app.blogapplication.responses.BlogResponse;
import com.javaenthu.blog.app.blogapplication.services.PostService;
import com.javaenthu.blog.app.blogapplication.utilities.Constants;

@RestController
@RequestMapping(value = "/api/v1/post")
@CrossOrigin(origins = "*")
public class PostController extends BaseController{
	
	
	@Autowired
	private PostService postServ;
	
	@GetMapping("/getAllPosts")
	public ResponseEntity<BlogResponse> getAllPosts(@RequestParam(defaultValue = "0") int startIndex, @RequestParam(defaultValue = "0") int pageSize,
	      @RequestParam(defaultValue = "id") String sortBy, @RequestParam(required = false) String search){
		Object response = postServ.getAllPost(startIndex, pageSize, search, sortBy);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	
	@GetMapping("/getPostById/{id}")
	public ResponseEntity<BlogResponse> getPostById(@PathVariable("id") int id){
		Object response = postServ.getPostById(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	
	@GetMapping("/getPostById/{id}/user/{userId}/category/{categoryId}")
	public ResponseEntity<BlogResponse> getCategoryById(@PathVariable("id") int id, @PathVariable("userId") int userId, @PathVariable("categoryId")
	               int categoryId){
		Object response = postServ.getPostById(id,userId,categoryId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	
	@GetMapping("/getAllPostByUser/{userId}")
	public ResponseEntity<BlogResponse> getAllPostByUser(@RequestParam(defaultValue = "0") int startIndex, @RequestParam(defaultValue = "0") int pageSize,
		      @RequestParam(defaultValue = "id") String sortBy, @RequestParam(required = false) String search,@PathVariable("userId") int userId){
		Object response = postServ.getAllPostByUser(startIndex,pageSize,userId, search,sortBy );
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	
	@GetMapping("/getAllPostByCategory/{categooryId}")
	public ResponseEntity<BlogResponse> getAllPostByCategory(@RequestParam(defaultValue = "0") int startIndex, @RequestParam(defaultValue = "0") int pageSize,
		      @RequestParam(defaultValue = "id") String sortBy, @RequestParam(required = false) String search,@PathVariable("categooryId") int categooryId){
		Object response = postServ.getAllPostByCategory(startIndex,pageSize,categooryId, search,sortBy );
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	
	@PostMapping("/createNewPost")
	public ResponseEntity<BlogResponse> createNewPost(@RequestBody PostDto dto){
		Object response = postServ.createNewPost(dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.CREATED.toString(), response));
	}
	
	@PutMapping("/updatePost")
	public ResponseEntity<BlogResponse> updatePost(@RequestBody PostDto dto){
		Object response = postServ.updatePost(dto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	
//	@DeleteMapping("/deletePostById/{id}")
//	public ResponseEntity<BlogResponse> deleteCategoryById(@PathVariable("id") int id){
//		postServ.deleteCategoryById(id);
//		return ResponseEntity.status(HttpStatus.OK)
//				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), "Category Deleted"));
//	}
	@PostMapping("/uploadImage")
	public ResponseEntity<BlogResponse> uploadImage(@RequestBody PostImageUploadDto dto){
		Object response = postServ.uploadImage(dto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}


}
