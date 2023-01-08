package com.javaenthu.blog.app.blogapplication.services;

import java.io.File;
import java.io.IOException;
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

import com.javaenthu.blog.app.blogapplication.exceptions.FileNotUploadException;
import com.javaenthu.blog.app.blogapplication.exceptions.NotFoundException;
import com.javaenthu.blog.app.blogapplication.models.Category;
import com.javaenthu.blog.app.blogapplication.models.Post;
import com.javaenthu.blog.app.blogapplication.models.User;
import com.javaenthu.blog.app.blogapplication.payloads.PostDto;
import com.javaenthu.blog.app.blogapplication.payloads.PostImageUploadDto;
import com.javaenthu.blog.app.blogapplication.repositories.CategoryRepository;
import com.javaenthu.blog.app.blogapplication.repositories.PostRepository;
import com.javaenthu.blog.app.blogapplication.repositories.UserRepository;
import com.javaenthu.blog.app.blogapplication.responses.PostResponse;
import com.javaenthu.blog.app.blogapplication.utilities.ApplicationUtil;
import com.javaenthu.blog.app.blogapplication.utilities.BlogErrors;
import com.javaenthu.blog.app.blogapplication.utilities.Constants;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ApplicationUtil applicationUtil;

	@Autowired
	private ModelMapper modelMapper;
	
	private  final String filePath = "F:\\FileStorageSpringBoot/";

	// getAllPost
	// getAllPostByUser
	// getAllPostByCategory
	// editpostbyuser
	// deletepostbyuser

	public Object getAllPost(int startIndex, int pageSize, String search, String sortBy) {
		Pageable page;
		Page<Post> pageResult;
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
		if (search == null) {
			pageResult = postRepo.findAll(page);
		} else {
			pageResult = postRepo.findByTitle(search, page);
		}
		Map<String, Object> response = new HashMap<String, Object>();
		if (pageResult == null || pageResult.getContent().isEmpty()) {
			response.put("totalElements", 0);
			response.put("totalPages", 0);
			response.put("posts", new ArrayList<>());
		} else {
			response.put("totalElements", pageResult.getTotalElements());
			response.put("totalPages", pageResult.getTotalPages());
			response.put("post", pageResult.getContent());
		}
		return response;
	}

	public Object getAllPostByUser(int startIndex, int pageSize, int user_id, String search, String sortBy) {
		Pageable page;
		Page<Post> pageResult;
		User user = userRepo.findById(user_id)
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND, "" + BlogErrors.USER_NOT_FOUND));
		if (pageSize == 0) {
			if (sortBy == "title") {
				page = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, sortBy));
			} else {
				page = PageRequest.of(pageSize, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
			}
		} else {
			int pageNumber = (startIndex + (pageSize - 1)) / pageSize;
			page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
		}
		if (search == null) {
			pageResult = postRepo.findAllPostByUser(user, page);
		} else {
			pageResult = postRepo.findByTitleAndUser(user, search, page);
		}
		Map<String, Object> response = new HashMap<String, Object>();
		if (pageResult == null || pageResult.getContent().isEmpty()) {
			response.put("totalElements", 0);
			response.put("totalPages", 0);
			response.put("posts", new ArrayList<>());
		} else {
			response.put("totalElements", pageResult.getTotalElements());
			response.put("totalPages", pageResult.getTotalPages());
			response.put("posts", pageResult.getContent());
		}
		return response;
	}

	public Object getAllPostByCategory(int startIndex, int pageSize, int category_id, String search, String sortBy) {
		Pageable page;
		Page<Post> pageResult;
		Category category = categoryRepo.findById(category_id)
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND, "" + BlogErrors.USER_NOT_FOUND));
		if (pageSize == 0) {
			if (sortBy == "title") {
				page = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, sortBy));
			} else {
				page = PageRequest.of(pageSize, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
			}
		} else {
			int pageNumber = (startIndex + (pageSize - 1)) / pageSize;
			page = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
		}
		if (search == null) {
			pageResult = postRepo.findAllPostByCategory(category, page);
		} else {
			pageResult = postRepo.findByTitleAndCategory(category, search, page);
		}
		Map<String, Object> response = new HashMap<String, Object>();
		if (pageResult == null || pageResult.getContent().isEmpty()) {
			response.put("totalElements", 0);
			response.put("totalPages", 0);
			response.put("posts", new ArrayList<>());
		} else {
			response.put("totalElements", pageResult.getTotalElements());
			response.put("totalPages", pageResult.getTotalPages());
			response.put("posts", pageResult.getContent());
		}
		return response;

	}

	public Object createNewPost(PostDto dto) {
		User user = userRepo.findById(dto.getUserId())
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND, "" + BlogErrors.USER_NOT_FOUND));
		Category category = categoryRepo.findById(dto.getCategoryId())
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND, "" + BlogErrors.USER_NOT_FOUND));
		Post post = new Post();
		post.setContent(dto.getContent());
		post.setTitle(dto.getTitle());
		post.setUser(user);
		post.setCategory(category);
		Post savedPost = postRepo.save(post);
		PostResponse response = modelMapper.map(savedPost, PostResponse.class);
		return response;
	}

	public Object updatePost(PostDto dto) {

		User user = userRepo.findById(dto.getUserId())
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND, "" + BlogErrors.USER_NOT_FOUND));
		Category category = categoryRepo.findById(dto.getCategoryId())
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND, "" + BlogErrors.USER_NOT_FOUND));
		Post post = postRepo.findById(dto.getId())
				.orElseThrow(() -> new NotFoundException(Constants.POST_NOT_FOUND, "" + BlogErrors.POST_NOT_FOUND));
		post.setCategory(category);
		post.setUser(user);
		post.setTitle(dto.getTitle());
		post.setImageName(dto.getImageName());
		Post updatedPost = postRepo.save(post);
		PostResponse response = modelMapper.map(updatedPost, PostResponse.class);
		return response;
	}

	public Object getPostById(int id, int userId, int categoryId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND, "" + BlogErrors.USER_NOT_FOUND));
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND, "" + BlogErrors.USER_NOT_FOUND));
		Post post = postRepo.findById(id)
				.orElseThrow(() -> new NotFoundException(Constants.POST_NOT_FOUND, "" + BlogErrors.POST_NOT_FOUND));
		PostResponse response = modelMapper.map(post, PostResponse.class);
		return response;
	}

	public Object uploadImage(PostImageUploadDto dto) {
		User user = userRepo.findById(dto.getUserId())
				.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND, "" + BlogErrors.USER_NOT_FOUND));
		Post post = postRepo.findById(dto.getPostId())
				.orElseThrow(() -> new NotFoundException(Constants.POST_NOT_FOUND, "" + BlogErrors.POST_NOT_FOUND));
        String imageNameToUpload = dto.getImage().getOriginalFilename().substring(0,
        		dto.getImage().getOriginalFilename().lastIndexOf(".")) +"_GUID_"
        		+applicationUtil.generateGUID()
        		+dto.getImage().getOriginalFilename().substring(dto.getImage().getOriginalFilename().lastIndexOf("."), 
        				dto.getImage().getOriginalFilename().length());
        //create folder if not exists
//        File file = new File(imageNameToUpload);
//        if(!file.exists()) {
//        	file.mkdir();
//        }
//        Files.cop
        String fullPath = filePath+imageNameToUpload;
        try {
			dto.getImage().transferTo(new File(fullPath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new FileNotUploadException(e.getMessage(),""+BlogErrors.FILE_NOT_UPLOAD_EXCEPTION );
		}
        return "file Uploaded Successfully"+fullPath;
        		
	}

	public Object getPostById(int id) {
		Post post = postRepo.findById(id)
				.orElseThrow(() -> new NotFoundException(Constants.POST_NOT_FOUND, "" + BlogErrors.POST_NOT_FOUND));
		PostResponse response = modelMapper.map(post, PostResponse.class);
		return response;
	}

}
