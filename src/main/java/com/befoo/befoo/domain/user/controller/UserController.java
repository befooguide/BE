package com.befoo.befoo.domain.user.controller;

import com.befoo.befoo.domain.oauth2.dto.CustomUserDetails;
import com.befoo.befoo.domain.user.dto.UserProfileRequest;
import com.befoo.befoo.domain.user.dto.UserProfileResponse;
import com.befoo.befoo.domain.user.entity.User;
import com.befoo.befoo.domain.user.service.UserManager;
import com.befoo.befoo.global.dto.ApiResponse;
import com.befoo.befoo.global.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserManager userManager;

    // 프로필 조회
    @GetMapping("/profile")
    public ApiResponse<Response> getProfile(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.user();
        log.info("GET 프로필: {}", user.getId());
        UserProfileResponse response = userManager.getProfile(user);
        return ApiResponse.success(response, "프로필 조회 성공");
    }

    // 프로필 수정
    @PutMapping("/profile")
    public ApiResponse<Response> putProfile(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody UserProfileRequest request) {
        User user = customUserDetails.user();
        log.info("PUT 프로필 수정: {}", user.getId());
        UserProfileResponse response = userManager.putProfile(user, request);
        return ApiResponse.success(response, "프로필 수정 성공");
    }

    // 나만의 목록 조회
    @GetMapping("/my-list")
    public ApiResponse<Response> getMyList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.user();
        log.info("GET 나만의 목록 조회: {}", user.getId());
        var response = userManager.getMyList(user);
        return ApiResponse.success(response, "나만의 목록 조회 성공");
    }

    // 저장 목록 조회
    @GetMapping("/bookmarked")
    public ApiResponse<Response> getBookmarkedList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.user();
        log.info("GET 저장 목록 조회: {}", user.getId());
        var response = userManager.getBookmarkedList(user);
        return ApiResponse.success(response, "저장 목록 조회 성공");
    }

    // 닉네임 중복 확인
    @GetMapping("/exists-username")
    public ApiResponse<Boolean> checkUsername(@RequestParam String username) {
        log.info("GET 닉네임 중복 확인: {}", username);
        boolean isAvailable = userManager.isUsernameAvailable(username);
        return ApiResponse.success(isAvailable, isAvailable ? "사용 가능한 닉네임입니다." : "이미 사용 중인 닉네임입니다.");
    }
}
