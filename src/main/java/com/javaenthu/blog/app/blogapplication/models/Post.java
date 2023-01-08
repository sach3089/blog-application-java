package com.javaenthu.blog.app.blogapplication.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "posts")
@Data
public class Post extends BaseIdEntity{
	
	public Post() {
		
	}

	@Column(name = "post_title")
	@NonNull
	private String title;
	
	@Column(name = "post_content")
	private String content;
	
	@Column(name = "image_name")
	private String imageName;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "post_id")
	private Category category;
	
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Comment> comments= new HashSet<>();
}
