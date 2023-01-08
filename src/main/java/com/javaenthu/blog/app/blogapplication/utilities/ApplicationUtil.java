package com.javaenthu.blog.app.blogapplication.utilities;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class ApplicationUtil {
	
	public String generateGUID() {
		UUID uuid = UUID.randomUUID();
		String uuidAsString = uuid.toString();
		return uuidAsString;
	}
	
	

}
