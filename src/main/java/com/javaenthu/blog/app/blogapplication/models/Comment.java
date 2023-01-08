package com.javaenthu.blog.app.blogapplication.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="post_comments")
@Data
public class Comment extends BaseIdEntity {
	
	@Column(name="user_comment")
	private String content;
	
	@ManyToOne
	private Post post;

}
