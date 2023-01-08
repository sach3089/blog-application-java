package com.javaenthu.blog.app.blogapplication.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javaenthu.blog.app.blogapplication.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
	@Query("select c from Category c where c.categoryName like %:search% ")
	Page<Category> findByName(String search, Pageable page);

	Category findByCategoryName(String string);

}
