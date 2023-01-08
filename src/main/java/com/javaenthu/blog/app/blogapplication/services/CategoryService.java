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
import com.javaenthu.blog.app.blogapplication.exceptions.ResourceExistException;
import com.javaenthu.blog.app.blogapplication.models.Category;
import com.javaenthu.blog.app.blogapplication.models.User;
import com.javaenthu.blog.app.blogapplication.payloads.CategoryDto;
import com.javaenthu.blog.app.blogapplication.payloads.UserDto;
import com.javaenthu.blog.app.blogapplication.repositories.CategoryRepository;
import com.javaenthu.blog.app.blogapplication.responses.CategoryResponse;
import com.javaenthu.blog.app.blogapplication.responses.UserResponse;
import com.javaenthu.blog.app.blogapplication.utilities.BlogErrors;
import com.javaenthu.blog.app.blogapplication.utilities.Constants;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	public Object getAllCategories(int startIndex, int pageSize, String search, String sortBy) {
		Pageable page;
		Page<Category> pageResult;
		if (pageSize == 0) {
			if (sortBy == "name") {
				page = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, sortBy));
			} else {
				page = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, sortBy));
			}
		} else {
			int pageNumber = (startIndex + (pageSize - 1)) / pageSize;
			page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
		}
		if (search == null) {
			pageResult = categoryRepo.findAll(page);
		} else {
			pageResult = categoryRepo.findByName(search, page);
		}
		Map<String, Object> response = new HashMap<String, Object>();
		if (pageResult == null || pageResult.getContent().isEmpty()) {
			response.put("totalElements", 0);
			response.put("totalPages", 0);
			response.put("category", new ArrayList<>());
		} else {
			response.put("totalElements", pageResult.getTotalElements());
			response.put("totalPages", pageResult.getTotalPages());
			response.put("categories", pageResult.getContent());
		}
		return response;
	}

	public Object getCategoryById(int id) {

		Category category = categoryRepo.findById(id)
				.orElseThrow(() -> new NotFoundException(Constants.CATEGORY_NOT_FOUND, "" + BlogErrors.CATEGORY_NOT_FOUND));
		CategoryResponse response = modelMapper.map(category, CategoryResponse.class);
		return response;

	}
	
	public Object createNewCategory(CategoryDto dto) {
		Category categoryExist = categoryRepo.findByCategoryName(dto.getCategoryName());
		if(categoryExist!=null) {
			throw new ResourceExistException(Constants.CATEGORY_NAME_ALREADY_EXIST, "" + BlogErrors.CATEGORY_NAME_ALREADY_EXISTS);
		}
		Category category = modelMapper.map(dto, Category.class);
		Category savedCategory = categoryRepo.save(category);
		CategoryResponse response = modelMapper.map(savedCategory, CategoryResponse.class);
		return response;
	}
	
	public Object updateCategory(CategoryDto dto) {
		Category category = categoryRepo.findById(dto.getId()).orElseThrow(() ->
                 new NotFoundException(Constants.CATEGORY_NOT_FOUND,""+  BlogErrors.CATEGORY_NOT_FOUND));
		category.setCategoryName(dto.getCategoryName());
		category.setCategoryDescription(dto.getCategoryDescription());
		Category updatedCategory = categoryRepo.save(category);
		CategoryResponse response = modelMapper.map(updatedCategory, CategoryResponse.class);
		return response;
	}
	
	public void deleteCategoryById(int id) {
		Category category = categoryRepo.findById(id).orElseThrow(() ->
                new NotFoundException(Constants.CATEGORY_NOT_FOUND,""+  BlogErrors.CATEGORY_NOT_FOUND));
		categoryRepo.delete(category);
		return;
	}


}
