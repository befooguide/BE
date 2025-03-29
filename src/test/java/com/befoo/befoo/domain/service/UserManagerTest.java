package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.dto.*;
import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.entity.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {

    @Mock
    private GuideManager guideManager;

    @Mock
    private PlaceManager placeManager;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserManager userManager;

    private User testUser;
    private GuideListResponse guideListResponse;
    private PlaceListResponse placeListResponse;

    @BeforeEach
    void setUp() {
        // 테스트용 사용자 객체 생성
        testUser = User.builder()
                .username("테스트사용자")
                .email("test@example.com")
                .role(Role.ROLE_USER)
                .healthConditions(new ArrayList<>())
                .allergies(new ArrayList<>())
                .build();

        // 테스트용 가이드 목록 응답 객체 생성
        guideListResponse = GuideListResponse.builder()
                .guides(new ArrayList<>())
                .build();

        // 테스트용 장소 목록 응답 객체 생성
        placeListResponse = PlaceListResponse.builder()
                .places(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("프로필 조회 테스트")
    void getProfileTest() {
        // when
        UserProfileResponse response = userManager.getProfile(testUser);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo(testUser.getId());
        assertThat(response.getUsername()).isEqualTo(testUser.getUsername());
    }

    @Test
    @DisplayName("프로필 수정 테스트")
    void putProfileTest() {
        // given
        // 실제 UserProfileRequest 객체를 생성하고 필요한 경우 spy를 사용
        when(userService.updateProfile(any(User.class), any(UserProfileRequest.class))).thenReturn(testUser);

        // when
        // UserProfileRequest 객체가 내부적으로 어떻게 사용되는지는 중요하지 않음
        // 중요한 것은 userService.updateProfile 호출과 반환값
        UserProfileResponse response = userManager.putProfile(testUser, new UserProfileRequest());

        // then
        assertThat(response).isNotNull();
        verify(userService, times(1)).updateProfile(any(User.class), any(UserProfileRequest.class));
    }

    @Test
    @DisplayName("나만의 목록 조회 테스트")
    void getMyListTest() {
        // given
        when(guideManager.getMyGuides(any(User.class))).thenReturn(guideListResponse);
        when(placeManager.getMyRecommendedPlaces(any(User.class))).thenReturn(placeListResponse);

        // when
        MyListResponse response = userManager.getMyList(testUser);

        // then
        assertThat(response).isNotNull();
        verify(guideManager, times(1)).getMyGuides(testUser);
        verify(placeManager, times(1)).getMyRecommendedPlaces(testUser);
    }

    @Test
    @DisplayName("저장 목록 조회 테스트")
    void getBookmarkedListTest() {
        // given
        when(guideManager.getBookmarkedGuides(any(User.class))).thenReturn(guideListResponse);
        when(placeManager.getMyBookmarkedPlaces(any(User.class))).thenReturn(placeListResponse);

        // when
        BookmarkedListResponse response = userManager.getBookmarkedList(testUser);

        // then
        assertThat(response).isNotNull();
        verify(guideManager, times(1)).getBookmarkedGuides(testUser);
        verify(placeManager, times(1)).getMyBookmarkedPlaces(testUser);
    }
} 