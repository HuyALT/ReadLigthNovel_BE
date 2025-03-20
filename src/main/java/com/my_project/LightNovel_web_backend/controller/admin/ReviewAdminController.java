package com.my_project.LightNovel_web_backend.controller.admin;

import com.my_project.LightNovel_web_backend.service.Review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/review")
public class ReviewAdminController {

    private final ReviewService reviewService;

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }
}
