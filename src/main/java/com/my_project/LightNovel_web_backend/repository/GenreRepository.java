package com.my_project.LightNovel_web_backend.repository;

import com.my_project.LightNovel_web_backend.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
