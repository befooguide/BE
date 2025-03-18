package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.dto.ReviewRequest;
import com.befoo.befoo.domain.dto.ReviewResponse;
import com.befoo.befoo.domain.entity.Place;
import com.befoo.befoo.domain.entity.Review;
import com.befoo.befoo.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceManager {
    private final PlaceService placeService;
    private final ReviewService reviewService;

    @Transactional
    public ReviewResponse createReview(String placeId, User user, ReviewRequest request) {
        Place place = placeService.findById(placeId);
        Review review = reviewService.createReview(place, user, request);
        return ReviewResponse.from(review);
    }

    @Transactional
    public ReviewResponse updateReview(String placeId, String reviewId, User user, ReviewRequest request) {
        Place place = placeService.findById(placeId);
        Review review = reviewService.updateReview(reviewId, user, request);
        reviewService.validateReviewBelongsToPlace(review, placeId);
        return ReviewResponse.from(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByPlaceId(String placeId) {
        Place place = placeService.findById(placeId);
        return reviewService.findReviewsByPlaceId(placeId)
                .stream()
                .map(ReviewResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReviewDetail(String placeId, String reviewId) {
        Place place = placeService.findById(placeId);
        Review review = reviewService.findById(reviewId);
        reviewService.validateReviewBelongsToPlace(review, placeId);
        return ReviewResponse.from(review);
    }
}
