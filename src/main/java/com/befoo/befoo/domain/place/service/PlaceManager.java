package com.befoo.befoo.domain.place.service;

import com.befoo.befoo.domain.place.dto.PlaceListResponse;
import com.befoo.befoo.domain.place.dto.PlaceResponse;
import com.befoo.befoo.domain.place.dto.ReviewListResponse;
import com.befoo.befoo.domain.place.dto.ReviewRequest;
import com.befoo.befoo.domain.place.dto.ReviewResponse;
import com.befoo.befoo.domain.place.entity.Place;
import com.befoo.befoo.domain.place.entity.Review;
import com.befoo.befoo.domain.user.entity.User;
import com.befoo.befoo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceManager {
    private final UserService userService;
    private final PlaceService placeService;
    private final ReviewService reviewService;
    private final BookmarkedPlaceService bookmarkedPlaceService;

    // API: 나만의 추천 식당 목록 조회
    @Transactional(readOnly = true)
    public PlaceListResponse getMyRecommendedPlaces(String username) {
        User user = userService.findUserByUsername(username);
        List<Place> places = reviewService.findRecommendedPlacesByUser(user);
        List<PlaceResponse> placeResponses = places.stream()
                .map(place -> PlaceResponse.from(place)
                        .withBookmarked(bookmarkedPlaceService.isBookmarked(user, place)))
                .toList();
        return PlaceListResponse.from(placeResponses);
    }

    // API: 평가 저장
    @Transactional
    public ReviewResponse createReview(String placeId, String username, ReviewRequest request) {
        User user = userService.findUserByUsername(username);
        Place place = placeService.findById(placeId);
        Review review = reviewService.createReview(place, user, request);
        return ReviewResponse.from(review);
    }

    // API: 평가 수정
    @Transactional
    public ReviewResponse updateReview(String placeId, String reviewId, String username, ReviewRequest request) {
        User user = userService.findUserByUsername(username);
        Review review = reviewService.updateReview(reviewId, user, request);
        reviewService.validateReviewBelongsToPlace(review, placeId);
        return ReviewResponse.from(review);
    }

    // API: 평가 목록 조회
    @Transactional(readOnly = true)
    public ReviewListResponse getReviewsByPlaceId(String placeId) {
        List<Review> reviews = reviewService.findReviewsByPlaceId(placeId);
        return ReviewListResponse.from(reviews);
    }

    // API: 평가 상세 조회
    @Transactional(readOnly = true)
    public ReviewResponse getReviewDetail(String placeId, String reviewId) {
        Review review = reviewService.findById(reviewId);
        reviewService.validateReviewBelongsToPlace(review, placeId);
        return ReviewResponse.from(review);
    }

    // API: 식당 목록 조회
    @Transactional(readOnly = true)
    public PlaceListResponse getPlaces(String username) {
        User user = userService.findUserByUsername(username);
        List<Place> places = placeService.getAllPlaces();
        List<PlaceResponse> placeResponses = places.stream()
                .map(place -> PlaceResponse.from(place)
                        .withBookmarked(bookmarkedPlaceService.isBookmarked(user, place)))
                .collect(Collectors.toList());
        return PlaceListResponse.from(placeResponses);
    }

    // API: 식당 상세 조회
    @Transactional(readOnly = true)
    public PlaceResponse getPlaceDetail(String placeId, String username) {
        User user = userService.findUserByUsername(username);
        Place place = placeService.getPlaceById(placeId);
        return PlaceResponse.from(place)
                .withBookmarked(bookmarkedPlaceService.isBookmarked(user, place));
    }

    // API: 저장한 식당 목록 조회
    @Transactional(readOnly = true)
    public PlaceListResponse getMyBookmarkedPlaces(String username) {
        User user = userService.findUserByUsername(username);
        List<Place> places = bookmarkedPlaceService.findBookmarkedPlacesByUser(user);
        List<PlaceResponse> placeResponses = places.stream()
                .map(place -> PlaceResponse.from(place)
                        .withBookmarked(true))
                .toList();
        return PlaceListResponse.from(placeResponses);
    }

    // API: 식당 저장
    @Transactional
    public PlaceResponse createBookmarkedPlace(String username, String placeId) {
        User user = userService.findUserByUsername(username);
        Place place = placeService.getPlaceById(placeId);
        bookmarkedPlaceService.createBookmarkedPlace(user, place);
        return PlaceResponse.from(place)
                .withBookmarked(true);
    }

    // API: 식당 저장 취소
    @Transactional
    public void deleteBookmarkedPlace(String username, String placeId) {
        User user = userService.findUserByUsername(username);
        Place place = placeService.getPlaceById(placeId);
        bookmarkedPlaceService.deleteBookmarkedPlace(user, place);
    }
}
