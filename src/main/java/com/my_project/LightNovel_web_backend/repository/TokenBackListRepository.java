package com.my_project.LightNovel_web_backend.repository;

import com.my_project.LightNovel_web_backend.entity.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBackListRepository extends JpaRepository<TokenBlackList, String> {
}
