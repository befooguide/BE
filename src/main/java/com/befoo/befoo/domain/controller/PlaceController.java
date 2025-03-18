package com.befoo.befoo.domain.controller;

import com.befoo.befoo.domain.dto.CustomUserDetails;
import com.befoo.befoo.domain.dto.ReviewRequest;
import com.befoo.befoo.domain.dto.ReviewResponse;
import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.service.PlaceManager;
import com.befoo.befoo.global.dto.ApiResponse;
import com.befoo.befoo.global.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/places")
public class PlaceController {
    private final PlaceManager placeManager;

    // 평가 저장
    @PostMapping("/{placeId}/reviews")
    public ApiResponse<Response> createReview(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @PathVariable("placeId") String placeId,
                                            @RequestBody ReviewRequest request) {
        User user = customUserDetails.user();
        log.info("POST 평가 저장: user-{}, place-{}", user.getId(), placeId);
        ReviewResponse response = placeManager.createReview(placeId, user, request);
        return ApiResponse.success(response, "평가 저장 성공");
    }

    // 평가 수정
    @PutMapping("/{placeId}/reviews/{reviewId}")
    public ApiResponse<Response> updateReview(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @PathVariable("placeId") String placeId,
                                            @PathVariable("reviewId") String reviewId,
                                            @RequestBody ReviewRequest request) {
        User user = customUserDetails.user();
        log.info("PUT 평가 수정: user-{}, place-{}, review-{}", user.getId(), placeId, reviewId);
        ReviewResponse response = placeManager.updateReview(placeId, reviewId, user, request);
        return ApiResponse.success(response, "평가 수정 성공");
    }

    // 평가 목록 조회
    @GetMapping("/{placeId}/reviews")
    public ApiResponse<List<ReviewResponse>> getReviews(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                        @PathVariable("placeId") String placeId) {
        User user = customUserDetails.user();
        log.info("GET 평가 목록 조회: user-{}, place-{}", user.getId(), placeId);
        List<ReviewResponse> response = placeManager.getReviewsByPlaceId(placeId);
        return ApiResponse.success(response, "평가 목록 조회 성공");
    }

    // 평가 상세 조회
    @GetMapping("/{placeId}/reviews/{reviewId}")
    public ApiResponse<ReviewResponse> getReviewDetail(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                       @PathVariable("placeId") String placeId,
                                                     @PathVariable("reviewId") String reviewId) {
        User user = customUserDetails.user();
        log.info("GET 평가 상세 조회: user-{}, place-{}, review-{}", user.getId(), placeId, reviewId);
        ReviewResponse response = placeManager.getReviewDetail(placeId, reviewId);
        return ApiResponse.success(response, "평가 상세 조회 성공");
    }
}
