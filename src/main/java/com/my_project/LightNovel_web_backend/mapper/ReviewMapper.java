package com.my_project.LightNovel_web_backend.mapper;

import com.my_project.LightNovel_web_backend.dto.request.ReviewRequest;
import com.my_project.LightNovel_web_backend.dto.response.ReviewResponse;
import com.my_project.LightNovel_web_backend.dto.response.UserResponse;
import com.my_project.LightNovel_web_backend.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review requestToEntity(ReviewRequest request) {
        Review review = new Review();
        review.setContent(request.getContent());
        review.setRating(request.getRating());
        return review;
    }

    public ReviewResponse entityToResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .updateAt(review.getUpdateAt())
                .lightNovelId(review.getLightNovel().getId())
                .userResponse(UserResponse.builder()
                                .userName(review.getUser().getUserName())
                                .image(review.getUser().getImage())
                                .build()
                )
                .build();
    }
}
