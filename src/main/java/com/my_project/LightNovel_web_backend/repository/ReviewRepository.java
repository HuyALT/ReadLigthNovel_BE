package com.my_project.LightNovel_web_backend.repository;

import com.my_project.LightNovel_web_backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.lightNovel.id = :lightNovelId")
    List<Review> findAllReviewByLH(@Param("lightNovelId") long lightNovelId);

    @Query("SELECT COALESCE(ROUND(AVG(r.rating), 1), 0) FROM Review r WHERE r.lightNovel.id = :lightNovelId")
    Float averageRating(@Param("lightNovelId") long lightNovelId);

    @Query("SELECT r FROM Review r WHERE r.user.userName = :userName AND r.lightNovel.id = :lightNovelId")
    Optional<Review> findByUserName(@Param("userName") String userName, @Param("lightNovelId") long lightNovelId);
}
