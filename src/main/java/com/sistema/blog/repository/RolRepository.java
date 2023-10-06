package com.sistema.blog.repository;

import com.sistema.blog.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    public Optional<Rol> findByName(String name);
}
