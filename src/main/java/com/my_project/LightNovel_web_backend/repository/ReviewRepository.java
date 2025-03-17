package com.my_project.LightNovel_web_backend.repository;

import com.my_project.LightNovel_web_backend.entity.Reveiw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Reveiw, Long> {
}
