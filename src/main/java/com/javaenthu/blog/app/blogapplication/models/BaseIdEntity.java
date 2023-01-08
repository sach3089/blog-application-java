package com.javaenthu.blog.app.blogapplication.models;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenerationTime;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;

@Audited
@Data
@MappedSuperclass
public class BaseIdEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	public int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
	@JsonIgnore
	private int createdBy;
	
	@JsonIgnore
	private int updatedBy;
	
	@Transient
	private String createdByUser;
	
	@Transient
	private String updatedByUser;
	
	

}
