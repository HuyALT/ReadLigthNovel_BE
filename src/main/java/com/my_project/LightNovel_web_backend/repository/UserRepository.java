package com.my_project.LightNovel_web_backend.repository;

import com.my_project.LightNovel_web_backend.dto.response.UserResponse;
import com.my_project.LightNovel_web_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserName(String username);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
