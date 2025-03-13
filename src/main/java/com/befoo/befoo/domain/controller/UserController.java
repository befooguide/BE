package com.befoo.befoo.domain.controller;

import com.befoo.befoo.domain.dto.CustomUserDetails;
import com.befoo.befoo.domain.dto.UserProfileHealthRequest;
import com.befoo.befoo.domain.dto.UserProfileResponse;
import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.service.UserManager;
import com.befoo.befoo.global.dto.ApiResponse;
import com.befoo.befoo.global.dto.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserManager userManager;

    // 회원 정보 조회
    @GetMapping("/profile")
    public ApiResponse<Response> getProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.user();
        log.info("GET 회원 정보 조회: {}", user.getId());
        UserProfileResponse response = userManager.getProfile(user);
        return ApiResponse.success(response, "회원 정보 조회 성공");
    }

    // 건강 정보 수정
    @PatchMapping("/profile/health")
    public ApiResponse<Response> patchProfileHealth(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserProfileHealthRequest request) {
        User user = customUserDetails.user();
        log.info("PATCH 건강 정보 수정: {}", user.getId());
        UserProfileResponse response = userManager.patchProfileHealth(user, request);
        return ApiResponse.success(response, "건강 정보 수정 성공");
    }

}
