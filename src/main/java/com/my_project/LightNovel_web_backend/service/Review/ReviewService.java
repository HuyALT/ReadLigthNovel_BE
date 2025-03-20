package com.my_project.LightNovel_web_backend.service.Review;

import com.my_project.LightNovel_web_backend.dto.request.ReviewRequest;
import com.my_project.LightNovel_web_backend.dto.response.ReviewResponse;
import com.my_project.LightNovel_web_backend.entity.LightNovel;
import com.my_project.LightNovel_web_backend.entity.Review;
import com.my_project.LightNovel_web_backend.entity.User;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.mapper.ReviewMapper;
import com.my_project.LightNovel_web_backend.repository.LightNovelRepository;
import com.my_project.LightNovel_web_backend.repository.ReviewRepository;
import com.my_project.LightNovel_web_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final LightNovelRepository lightNovelRepository;

    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewResponse addReview(long lightNovelId, String userName, ReviewRequest request) {
        Review review = reviewMapper.requestToEntity(request);
        User user = userRepository.findByUserName(userName).orElseThrow(
                ()->new AppException(ErrorCode.UNAUTHENTICATED, userName)
        );
        LightNovel lightNovel = lightNovelRepository.findById(lightNovelId).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, lightNovelId)
        );
        review.setUser(user);
        review.setLightNovel(lightNovel);
        return reviewMapper.entityToResponse(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public ReviewResponse editReview(long reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, reviewId)
        );
        review.setContent(request.getContent());
        review.setRating(request.getRating());
        return reviewMapper.entityToResponse(review);
    }

    @Override
    public List<ReviewResponse> findAllByLightNovel(long lightNovelId) {
        List<Review> reviews = reviewRepository.findAllReviewByLH(lightNovelId);
        if (reviews.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND,lightNovelId);
        }

        return reviews.stream().map(reviewMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public float averageRating(long lightNovelId) {

        return reviewRepository.averageRating(lightNovelId);
    }

    @Override
    public ReviewResponse findByUserName(String userName, long lightNovelId) {
        Review review = reviewRepository.findByUserName(userName, lightNovelId).orElseThrow(
                ()->new AppException(ErrorCode.NOT_FOUND, userName)
        );
        return reviewMapper.entityToResponse(review);
    }

    @Override
    @Transactional
    public void deleteReview(long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, reviewId)
        );
        reviewRepository.delete(review);
    }
}
