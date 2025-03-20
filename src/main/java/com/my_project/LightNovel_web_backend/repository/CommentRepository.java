package com.my_project.LightNovel_web_backend.repository;

import com.my_project.LightNovel_web_backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT cm FROM Comment cm WHERE cm.chapter.id = :chapterId")
    List<Comment> findByChapterId(@Param("chapterId") long chapterId);
}
