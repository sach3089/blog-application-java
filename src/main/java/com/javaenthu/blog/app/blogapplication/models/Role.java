package com.javaenthu.blog.app.blogapplication.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name ="role")
@Data
public class Role {
	
	@Id
	private int id;
	private String name;

}
