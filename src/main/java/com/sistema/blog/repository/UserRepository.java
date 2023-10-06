package com.sistema.blog.repository;

import com.sistema.blog.entities.UserBlog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserBlog, Long> {

    public Optional<UserBlog> findByEmail(String email);

    public Optional<UserBlog> findByUsernameOrEmail(String username, String email);

    public Optional<UserBlog> findByUsername(String username);

    public Boolean existsByUsername(String username);

    public Boolean existsByEmail(String email);
}
