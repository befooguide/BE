package com.befoo.befoo.domain.guide.controller;

import com.befoo.befoo.domain.oauth2.dto.CustomUserDetails;
import com.befoo.befoo.domain.guide.dto.GuideListResponse;
import com.befoo.befoo.domain.guide.dto.GuideRequest;
import com.befoo.befoo.domain.guide.dto.GuideResponse;
import com.befoo.befoo.domain.user.entity.User;
import com.befoo.befoo.domain.guide.service.GuideManager;
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
    public ApiResponse<GuideListResponse> getGuides(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails != null ? customUserDetails.user() : null;
        log.info("GET 가이드 목록 조회: user-{}", user != null ? user.getId() : "anonymous");
        GuideListResponse response = guideManager.getGuides(user);
        return ApiResponse.success(response, "가이드 목록 조회 성공");
    }

    // 가이드 상세 조회
    @GetMapping("/{guideId}")
    public ApiResponse<GuideResponse> getGuideDetail(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String guideId) {
        User user = customUserDetails != null ? customUserDetails.user() : null;
        log.info("GET 가이드 상세 조회: user-{}, guide-{}", user != null ? user.getId() : "anonymous", guideId);
        GuideResponse response = guideManager.getGuideDetail(guideId, user);
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

    // 가이드 저장
    @PostMapping("/{guideId}/bookmarked")
    public ApiResponse<Response> bookmarkGuide(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String guideId) {
        User user = customUserDetails.user();
        log.info("POST 가이드 저장: user-{}, guide-{}", user.getId(), guideId);
        GuideResponse response = guideManager.bookmarkGuide(guideId, user);
        return ApiResponse.created(response, "가이드 저장 성공");
    }

    // 저장 가이드 목록 조회
    @GetMapping("/bookmarked")
    public ApiResponse<GuideListResponse> getBookmarkedGuides(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.user();
        log.info("GET 저장 가이드 목록 조회: user-{}", user.getId());
        GuideListResponse response = guideManager.getBookmarkedGuides(user);
        return ApiResponse.success(response, "저장 가이드 목록 조회 성공");
    }

    // 가이드 저장 취소
    @DeleteMapping("/{guideId}/bookmarked")
    public ApiResponse<Void> unbookmarkGuide(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String guideId) {
        User user = customUserDetails.user();
        log.info("DELETE 가이드 저장 취소: user-{}, guide-{}", user.getId(), guideId);
        guideManager.unbookmarkGuide(guideId, user);
        return ApiResponse.noContent("가이드 저장 취소 성공");
    }
}
