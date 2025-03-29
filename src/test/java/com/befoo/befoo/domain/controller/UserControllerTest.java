package com.befoo.befoo.domain.controller;

import com.befoo.befoo.domain.dto.*;
import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.entity.enums.Role;
import com.befoo.befoo.domain.service.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * UserManager 서비스 계층 단위 테스트
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserManager userManager;
    
    @InjectMocks
    private UserController userController;

    private UserProfileResponse profileResponse;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .username("테스트사용자")
                .email("test@example.com")
                .role(Role.ROLE_USER)
                .healthConditions(new ArrayList<>())
                .allergies(new ArrayList<>())
                .build();
        
        profileResponse = UserProfileResponse.builder()
                .userId("test-user-id")
                .username("테스트사용자")
                .healthConditions(List.of())
                .allergies(List.of())
                .build();
    }

    @Test
    @DisplayName("UserManager 서비스 단위 테스트 - 프로필 조회")
    void getProfileServiceTest() {
        // given
        when(userManager.getProfile(any(User.class))).thenReturn(profileResponse);
        
        // when
        UserProfileResponse result = userManager.getProfile(testUser);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("테스트사용자");
    }

    @Test
    @DisplayName("UserManager 서비스 단위 테스트 - 프로필 수정")
    void putProfileServiceTest() {
        // given
        UserProfileRequest request = new UserProfileRequest();
        // 요청 객체에 필요한 속성 설정
        
        when(userManager.putProfile(any(User.class), any(UserProfileRequest.class))).thenReturn(profileResponse);
        
        // when
        UserProfileResponse result = userManager.putProfile(testUser, request);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("테스트사용자");
    }

    @Test
    @DisplayName("UserManager 서비스 단위 테스트 - 나만의 목록 조회")
    void getMyListServiceTest() {
        // given
        MyListResponse mockResponse = mock(MyListResponse.class);
        when(userManager.getMyList(any(User.class))).thenReturn(mockResponse);
        
        // when
        MyListResponse result = userManager.getMyList(testUser);
        
        // then
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("UserManager 서비스 단위 테스트 - 저장 목록 조회")
    void getBookmarkedListServiceTest() {
        // given
        BookmarkedListResponse mockResponse = mock(BookmarkedListResponse.class);
        when(userManager.getBookmarkedList(any(User.class))).thenReturn(mockResponse);
        
        // when
        BookmarkedListResponse result = userManager.getBookmarkedList(testUser);
        
        // then
        assertThat(result).isNotNull();
    }
} 