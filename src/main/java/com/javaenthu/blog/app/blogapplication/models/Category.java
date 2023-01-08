package com.javaenthu.blog.app.blogapplication.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;


@Entity
@Table(name = "categories")
@Data
public class Category extends BaseIdEntity{
	
	@Column(name = "category_name")
	private String categoryName;
	
	@Column(name = "category_desc")
	private String categoryDescription;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Post> post =new ArrayList<Post>();
	
}
