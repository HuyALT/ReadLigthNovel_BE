package com.my_project.LightNovel_web_backend.controller.user;

import com.my_project.LightNovel_web_backend.dto.request.ReviewRequest;
import com.my_project.LightNovel_web_backend.service.Review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/review")
public class ReviewUserController {

    private final ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@AuthenticationPrincipal Jwt jwt,
                                       @RequestParam long lightNovelId,
                                       @RequestBody ReviewRequest request) {
        String userName = jwt.getSubject();
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.addReview(lightNovelId, userName, request));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> editReview(@PathVariable long reviewId,
                                        @RequestBody ReviewRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.editReview(reviewId, request));
    }

    @GetMapping
    public ResponseEntity<?> getByUserName(@AuthenticationPrincipal Jwt jwt,
                                           @RequestParam long lightNovelId) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.findByUserName(jwt.getSubject(), lightNovelId));
    }
}
