package com.javaenthu.blog.app.blogapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaenthu.blog.app.blogapplication.payloads.CategoryDto;
import com.javaenthu.blog.app.blogapplication.payloads.UserDto;
import com.javaenthu.blog.app.blogapplication.responses.BlogResponse;
import com.javaenthu.blog.app.blogapplication.services.CategoryService;
import com.javaenthu.blog.app.blogapplication.utilities.Constants;

@RestController
@RequestMapping(value ="/api/v1/category")
public class CategoryController extends BaseController{
	
	@Autowired
	private CategoryService categoryServ;
	
	
	@GetMapping("/getAllCategories")
	public ResponseEntity<BlogResponse> getAllCategories(@RequestParam(defaultValue = "0") int startIndex, @RequestParam(defaultValue = "0") int pageSize,
	      @RequestParam(defaultValue = "id") String sortBy, @RequestParam(required = false) String search){
		Object response = categoryServ.getAllCategories(startIndex, pageSize, search, sortBy);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	@GetMapping("/getCategoryById/{id}")
	public ResponseEntity<BlogResponse> getCategoryById(@PathVariable("id") int id){
		Object response = categoryServ.getCategoryById(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	
	@PostMapping("/createNewCategory")
	public ResponseEntity<BlogResponse> createNewCategory(@RequestBody CategoryDto dto){
		Object response = categoryServ.createNewCategory(dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.CREATED.toString(), response));
	}
	
	@PutMapping("/updateCategory")
	public ResponseEntity<BlogResponse> updateCategory(@RequestBody CategoryDto dto){
		Object response = categoryServ.updateCategory(dto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), response));
	}
	
	@DeleteMapping("/deleteCategoryById/{id}")
	public ResponseEntity<BlogResponse> deleteCategoryById(@PathVariable("id") int id){
		categoryServ.deleteCategoryById(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildBlogResponse(Constants.SUCCESS, HttpStatus.OK.toString(), "Category Deleted"));
	}

	

}
