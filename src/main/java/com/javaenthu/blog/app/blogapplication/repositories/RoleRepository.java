package com.javaenthu.blog.app.blogapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaenthu.blog.app.blogapplication.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
