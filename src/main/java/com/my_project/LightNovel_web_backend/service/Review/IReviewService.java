package com.my_project.LightNovel_web_backend.service.Review;

import com.my_project.LightNovel_web_backend.dto.request.ReviewRequest;
import com.my_project.LightNovel_web_backend.dto.response.ReviewResponse;

import java.util.List;

public interface IReviewService {
    ReviewResponse addReview(long lightNovelId, String userName, ReviewRequest request);
    ReviewResponse editReview(long reviewId, ReviewRequest request);
    List<ReviewResponse> findAllByLightNovel(long lightNovelId);
    float averageRating(long lightNovelId);
    ReviewResponse findByUserName(String userName, long lightNovelId);
    void deleteReview(long reviewId);
}
