package com.my_project.LightNovel_web_backend.repository;

import com.my_project.LightNovel_web_backend.entity.LightNovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LightNovelRepository extends JpaRepository<LightNovel, Long> {

    @Query("SELECT l FROM LightNovel l JOIN l.genres g WHERE g.name IN :genres")
    Page<LightNovel> findByGeners(@Param("genres") List<String> genres, Pageable pageable);

    @Query("SELECT l FROM LightNovel l LEFT JOIN l.chapters ch GROUP BY l ORDER BY COALESCE(MAX(ch.updateAt), '2025-01-01') DESC")
    Page<LightNovel> findByLatestChapterUpdate(Pageable pageable);

    @Query("SELECT l FROM LightNovel l JOIN l.genres g LEFT JOIN l.chapters c " +
            "WHERE g.name IN :genres " +
            "ORDER BY MAX(c.updateAt) DESC")
    Page<LightNovel> findByGenersSortByLastestChapterUpdate(@Param("genres") List<String> genres, Pageable pageable);

}
