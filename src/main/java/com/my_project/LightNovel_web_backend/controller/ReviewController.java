package com.my_project.LightNovel_web_backend.controller;

import com.my_project.LightNovel_web_backend.service.Review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> findAllReview(@RequestParam long lightNovelId) {

        return ResponseEntity.status(HttpStatus.OK).body(reviewService.findAllByLightNovel(lightNovelId));
    }

    @GetMapping("/averageRating")
    public ResponseEntity<?> averageRating(@RequestParam long lightNovelId) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.averageRating(lightNovelId));
    }
}
