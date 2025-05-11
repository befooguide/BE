package com.befoo.befoo.domain.place.controller;

import com.befoo.befoo.domain.oauth2.dto.CustomOAuth2User;
import com.befoo.befoo.domain.place.dto.PlaceListResponse;
import com.befoo.befoo.domain.place.dto.PlaceResponse;
import com.befoo.befoo.domain.place.dto.ReviewListResponse;
import com.befoo.befoo.domain.place.dto.ReviewRequest;
import com.befoo.befoo.domain.place.dto.ReviewResponse;
import com.befoo.befoo.domain.place.service.PlaceManager;
import com.befoo.befoo.global.dto.ApiResponse;
import com.befoo.befoo.global.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/places")
public class PlaceController {
    private final PlaceManager placeManager;

    // 나만의 추천 식당 목록 조회
    @GetMapping("/my-list")
    public ApiResponse<Response> getMyRecommendedPlaces(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        String username = oAuth2User.getName();
        log.info("GET 나만의 추천 식당 목록 조회: user-{}", username);
        PlaceListResponse response = placeManager.getMyRecommendedPlaces(username);
        return ApiResponse.success(response, "나만의 추천 식당 목록 조회 성공");
    }

    // 평가 저장
    @PostMapping("/{placeId}/reviews")
    public ApiResponse<Response> createReview(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @PathVariable String placeId,
            @RequestBody ReviewRequest request) {
        String username = oAuth2User.getName();
        log.info("POST 평가 저장: user-{}, place-{}", username, placeId);
        ReviewResponse response = placeManager.createReview(placeId, username, request);
        return ApiResponse.created(response, "평가 저장 성공");
    }

    // 평가 수정
    @PutMapping("/{placeId}/reviews/{reviewId}")
    public ApiResponse<Response> updateReview(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @PathVariable String placeId,
            @PathVariable String reviewId,
            @RequestBody ReviewRequest request) {
        String username = oAuth2User.getName();
        log.info("PUT 평가 수정: user-{}, place-{}, review-{}", username, placeId, reviewId);
        ReviewResponse response = placeManager.updateReview(placeId, reviewId, username, request);
        return ApiResponse.success(response, "평가 수정 성공");
    }

    // 평가 목록 조회
    @GetMapping("/{placeId}/reviews")
    public ApiResponse<Response> getReviewsByPlaceId(
            @PathVariable String placeId) {
        log.info("GET 평가 목록 조회: place-{}", placeId);
        ReviewListResponse response = placeManager.getReviewsByPlaceId(placeId);
        return ApiResponse.success(response, "평가 목록 조회 성공");
    }

    // 평가 상세 조회
    @GetMapping("/{placeId}/reviews/{reviewId}")
    public ApiResponse<Response> getReviewDetail(
            @PathVariable String placeId,
            @PathVariable String reviewId) {
        log.info("GET 평가 상세 조회: place-{}, review-{}", placeId, reviewId);
        ReviewResponse response = placeManager.getReviewDetail(placeId, reviewId);
        return ApiResponse.success(response, "평가 상세 조회 성공");
    }

    // 식당 저장
    @PostMapping("/{placeId}/bookmarked")
    public ApiResponse<Response> createBookmarkedPlace(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @PathVariable String placeId) {
        String username = oAuth2User.getName();
        log.info("POST 식당 저장: user-{}, place-{}", username, placeId);
        PlaceResponse response = placeManager.createBookmarkedPlace(username, placeId);
        return ApiResponse.created(response, "식당 저장 성공");
    }

    // 저장한 식당 목록 조회
    @GetMapping("/bookmarked")
    public ApiResponse<Response> getMyBookmarkedPlaces(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        String username = oAuth2User.getName();
        log.info("GET 저장한 식당 목록 조회: user-{}", username);
        PlaceListResponse response = placeManager.getMyBookmarkedPlaces(username);
        return ApiResponse.success(response, "저장한 식당 목록 조회 성공");
    }

    // 식당 저장 취소
    @DeleteMapping("/{placeId}/bookmarked")
    public ApiResponse<Void> deleteBookmarkedPlace(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @PathVariable String placeId) {
        String username = oAuth2User.getName();
        log.info("DELETE 식당 저장 취소: user-{}, place-{}", username, placeId);
        placeManager.deleteBookmarkedPlace(username, placeId);
        return ApiResponse.noContent("식당 저장 취소 성공");
    }
}
