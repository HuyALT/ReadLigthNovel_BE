package com.my_project.LightNovel_web_backend.repository;

import com.my_project.LightNovel_web_backend.entity.LigthNovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LigthNovelRepository extends JpaRepository<LigthNovel, Long> {

    @Query("SELECT l FROM LigthNovel l JOIN l.genres g WHERE g.name IN :genres")
    Page<LigthNovel> findByGeners(@Param("genres") List<String> genres, Pageable pageable);

    @Query("SELECT l FROM LigthNovel l JOIN l.chapters ch GROUP BY l ORDER BY MAX(ch.updateAt)")
    Page<LigthNovel> findByLatestChapterUpdate(Pageable pageable);

    @Query("SELECT l FROM LightMovel l JOIN l.genres g LEFT JOIN l.chapters c WHERE g.name IN :genres GROUP BY l.id ORDER BY MAX(c.updateAt)")
    Page<LigthNovel> findByGenersSortByLastestChapterUpdate(List<String> genres, Pageable pageable);

}
