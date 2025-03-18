package com.befoo.befoo.domain.controller;

import com.befoo.befoo.domain.dto.CustomUserDetails;
import com.befoo.befoo.domain.dto.GuideListResponse;
import com.befoo.befoo.domain.dto.GuideRequest;
import com.befoo.befoo.domain.dto.GuideResponse;
import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.service.GuideManager;
import com.befoo.befoo.global.dto.ApiResponse;
import com.befoo.befoo.global.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guides")
public class GuideController {
    private final GuideManager guideManager;

    // 가이드 생성
    @PostMapping
    public ApiResponse<Response> createGuide(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody GuideRequest request) {
        User user = customUserDetails.user();
        log.info("POST 가이드 생성: user-{}", user.getId());
        GuideResponse response = guideManager.createGuide(user, request);
        return ApiResponse.created(response, "가이드 생성 성공");
    }

    // 가이드 수정
    @PutMapping("/{guideId}")
    public ApiResponse<Response> updateGuide(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String guideId,
            @RequestBody GuideRequest request) {
        User user = customUserDetails.user();
        log.info("PUT 가이드 수정: user-{}, guide-{}", user.getId(), guideId);
        GuideResponse response = guideManager.updateGuide(guideId, user, request);
        return ApiResponse.success(response, "가이드 수정 성공");
    }

    // 가이드 삭제
    @DeleteMapping("/{guideId}")
    public ApiResponse<Void> deleteGuide(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String guideId) {
        User user = customUserDetails.user();
        log.info("DELETE 가이드 삭제: user-{}, guide-{}", user.getId(), guideId);
        guideManager.deleteGuide(guideId, user);
        return ApiResponse.noContent("가이드 삭제 성공");
    }

    // 가이드 목록 조회
    @GetMapping
    public ApiResponse<GuideListResponse> getGuides() {
        log.info("GET 가이드 목록 조회");
        GuideListResponse response = guideManager.getGuides();
        return ApiResponse.success(response, "가이드 목록 조회 성공");
    }

    // 가이드 상세 조회
    @GetMapping("/{guideId}")
    public ApiResponse<GuideResponse> getGuideDetail(@PathVariable String guideId) {
        log.info("GET 가이드 상세 조회: guide-{}", guideId);
        GuideResponse response = guideManager.getGuideDetail(guideId);
        return ApiResponse.success(response, "가이드 상세 조회 성공");
    }

    // 나만의 가이드 목록 조회
    @GetMapping("/my-list")
    public ApiResponse<GuideListResponse> getMyGuides(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.user();
        log.info("GET 나만의 가이드 목록 조회: user-{}", user.getId());
        GuideListResponse response = guideManager.getMyGuides(user);
        return ApiResponse.success(response, "나만의 가이드 목록 조회 성공");
    }
}
