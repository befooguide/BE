package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.dto.ReviewRequest;
import com.befoo.befoo.domain.entity.Place;
import com.befoo.befoo.domain.entity.Review;
import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.exception.PlaceException;
import com.befoo.befoo.domain.exception.ReviewException;
import com.befoo.befoo.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public Review createReview(Place place, User user, ReviewRequest request) {
        validateScore(request);
        Review review = Review.builder()
                .user(user)
                .place(place)
                .receipt(request.getReceipt())
                .taste(request.getTaste())
                .menu(request.getMenu())
                .nutrition(request.getNutrition())
                .health(request.getHealth())
                .totalScore(request.getTotalScore())
                .comment(request.getComment())
                .recommend(request.isRecommend())
                .build();

        return reviewRepository.save(review);
    }

    @Transactional
    public Review updateReview(String reviewId, User user, ReviewRequest request) {
        Review review = findById(reviewId);
        validateReviewOwner(review, user);
        validateScore(request);

        review.update(
                request.getTaste(),
                request.getMenu(),
                request.getNutrition(),
                request.getHealth(),
                request.getTotalScore(),
                request.getComment()
        );

        return reviewRepository.save(review);
    }

    public List<Review> findReviewsByPlaceId(String placeId) {
        return reviewRepository.findByPlaceId(placeId);
    }

    public Review findById(String reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> ReviewException.notFound(reviewId));
    }

    public void validateReviewBelongsToPlace(Review review, String placeId) {
        if (!review.getPlace().getId().equals(placeId)) {
            throw PlaceException.invalidReview(placeId);
        }
    }

    private void validateReviewOwner(Review review, User user) {
        if (!review.getUser().getId().equals(user.getId())) {
            throw ReviewException.notAuthorized(review.getId());
        }
    }

    private void validateScore(ReviewRequest request) {
        if (request.getTaste() < 0 || request.getTaste() > 5 ||
            request.getMenu() < 0 || request.getMenu() > 5 ||
            request.getNutrition() < 0 || request.getNutrition() > 5 ||
            request.getHealth() < 0 || request.getHealth() > 5 ||
            request.getTotalScore() < 0 || request.getTotalScore() > 5) {
            throw ReviewException.invalidScore("new");
        }
    }
} 