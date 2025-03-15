package com.my_project.LightNovel_web_backend.repository;

import com.my_project.LightNovel_web_backend.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

}
