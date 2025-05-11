package com.befoo.befoo.domain.guide.controller;

import com.befoo.befoo.domain.oauth2.dto.CustomOAuth2User;
import com.befoo.befoo.domain.guide.dto.GuideListResponse;
import com.befoo.befoo.domain.guide.dto.GuideRequest;
import com.befoo.befoo.domain.guide.dto.GuideResponse;
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
            @AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @RequestBody GuideRequest request) {
        String username = oAuth2User.getName();
        log.info("POST 가이드 생성: user-{}", username);
        GuideResponse response = guideManager.createGuide(username, request);
        return ApiResponse.created(response, "가이드 생성 성공");
    }

    // 가이드 수정
    @PutMapping("/{guideId}")
    public ApiResponse<Response> updateGuide(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @PathVariable String guideId,
            @RequestBody GuideRequest request) {
        String username = oAuth2User.getName();
        log.info("PUT 가이드 수정: user-{}, guide-{}", username, guideId);
        GuideResponse response = guideManager.updateGuide(guideId, username, request);
        return ApiResponse.success(response, "가이드 수정 성공");
    }

    // 가이드 삭제
    @DeleteMapping("/{guideId}")
    public ApiResponse<Void> deleteGuide(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @PathVariable String guideId) {
        String username = oAuth2User.getName();
        log.info("DELETE 가이드 삭제: user-{}, guide-{}", username, guideId);
        guideManager.deleteGuide(guideId, username);
        return ApiResponse.noContent("가이드 삭제 성공");
    }

    // 가이드 목록 조회
    @GetMapping
    public ApiResponse<GuideListResponse> getGuides(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        String username = oAuth2User.getName();
        log.info("GET 가이드 목록 조회: user-{}", username);
        GuideListResponse response = guideManager.getGuides(username);
        return ApiResponse.success(response, "가이드 목록 조회 성공");
    }

    // 가이드 상세 조회
    @GetMapping("/{guideId}")
    public ApiResponse<GuideResponse> getGuideDetail(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @PathVariable String guideId) {
        String username = oAuth2User.getName();
        log.info("GET 가이드 상세 조회: user-{}, guide-{}", username, guideId);
        GuideResponse response = guideManager.getGuideDetail(guideId, username);
        return ApiResponse.success(response, "가이드 상세 조회 성공");
    }

    // 나만의 가이드 목록 조회
    @GetMapping("/my-list")
    public ApiResponse<GuideListResponse> getMyGuides(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        String username = oAuth2User.getName();
        log.info("GET 나만의 가이드 목록 조회: user-{}", username);
        GuideListResponse response = guideManager.getMyGuides(username);
        return ApiResponse.success(response, "나만의 가이드 목록 조회 성공");
    }

    // 가이드 저장
    @PostMapping("/{guideId}/bookmarked")
    public ApiResponse<Response> bookmarkGuide(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @PathVariable String guideId) {
        String username = oAuth2User.getName();
        log.info("POST 가이드 저장: user-{}, guide-{}", username, guideId);
        GuideResponse response = guideManager.bookmarkGuide(guideId, username);
        return ApiResponse.created(response, "가이드 저장 성공");
    }

    // 저장 가이드 목록 조회
    @GetMapping("/bookmarked")
    public ApiResponse<GuideListResponse> getBookmarkedGuides(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        String username = oAuth2User.getName();
        log.info("GET 저장 가이드 목록 조회: user-{}", username);
        GuideListResponse response = guideManager.getBookmarkedGuides(username);
        return ApiResponse.success(response, "저장 가이드 목록 조회 성공");
    }

    // 가이드 저장 취소
    @DeleteMapping("/{guideId}/bookmarked")
    public ApiResponse<Void> unbookmarkGuide(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User,
            @PathVariable String guideId) {
        String username = oAuth2User.getName();
        log.info("DELETE 가이드 저장 취소: user-{}, guide-{}", username, guideId);
        guideManager.unbookmarkGuide(guideId, username);
        return ApiResponse.noContent("가이드 저장 취소 성공");
    }
}
