package com.sistema.blog.repository;

import com.sistema.blog.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts, Long> {
}
