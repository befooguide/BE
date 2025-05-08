package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.guide.entity.BookmarkedGuide;
import com.befoo.befoo.domain.guide.entity.Guide;
import com.befoo.befoo.domain.guide.service.BookmarkedGuideService;
import com.befoo.befoo.domain.guide.service.GuideService;
import com.befoo.befoo.domain.place.entity.BookmarkedPlace;
import com.befoo.befoo.domain.place.entity.Place;
import com.befoo.befoo.domain.place.service.BookmarkedPlaceService;
import com.befoo.befoo.domain.place.service.ReviewService;
import com.befoo.befoo.domain.user.dto.BookmarkedListResponse;
import com.befoo.befoo.domain.user.dto.MyListResponse;
import com.befoo.befoo.domain.user.dto.UserProfileRequest;
import com.befoo.befoo.domain.user.dto.UserProfileResponse;
import com.befoo.befoo.domain.user.entity.enums.Role;
import com.befoo.befoo.domain.user.entity.User;
import com.befoo.befoo.domain.user.service.UserManager;
import com.befoo.befoo.domain.user.service.UserService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {

    @Mock
    private UserService userService;

    @Mock
    private GuideService guideService;

    @Mock
    private ReviewService reviewService;

    @Mock
    private BookmarkedGuideService bookmarkedGuideService;

    @Mock
    private BookmarkedPlaceService bookmarkedPlaceService;

    @InjectMocks
    private UserManager userManager;

    private User testUser;
    private List<Guide> testGuides;
    private List<Place> testPlaces;
    private List<BookmarkedGuide> testBookmarkedGuides;
    private List<BookmarkedPlace> testBookmarkedPlaces;

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

        // 테스트용 가이드 목록 생성
        testGuides = new ArrayList<>();
        testGuides.add(Guide.builder()
                .id("1")
                .name("테스트 가이드")
                .description("테스트 가이드 설명")
                .user(testUser)
                .build());

        // 테스트용 장소 목록 생성
        testPlaces = new ArrayList<>();
        testPlaces.add(Place.builder()
                .id("1")
                .name("테스트 장소")
                .description("테스트 장소 설명")
                .reviews(new ArrayList<>())
                .build());

        // 테스트용 북마크된 가이드 목록 생성
        testBookmarkedGuides = new ArrayList<>();
        testBookmarkedGuides.add(BookmarkedGuide.builder()
                .id("1")
                .user(testUser)
                .guide(testGuides.get(0))
                .build());

        // 테스트용 북마크된 장소 목록 생성
        testBookmarkedPlaces = new ArrayList<>();
        testBookmarkedPlaces.add(BookmarkedPlace.builder()
                .id("1")
                .user(testUser)
                .place(testPlaces.get(0))
                .build());
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
        when(userService.updateProfile(any(User.class), any(UserProfileRequest.class))).thenReturn(testUser);

        // when
        UserProfileResponse response = userManager.putProfile(testUser, new UserProfileRequest());

        // then
        assertThat(response).isNotNull();
        verify(userService, times(1)).updateProfile(any(User.class), any(UserProfileRequest.class));
    }

    @Test
    @DisplayName("나만의 목록 조회 테스트")
    void getMyListTest() {
        // given
        when(guideService.findByUser(any(User.class))).thenReturn(testGuides);
        when(reviewService.findRecommendedPlacesByUser(any(User.class))).thenReturn(testPlaces);
        when(bookmarkedGuideService.isBookmarked(any(User.class), any(Guide.class))).thenReturn(false);
        when(bookmarkedPlaceService.isBookmarked(any(User.class), any(Place.class))).thenReturn(false);

        // when
        MyListResponse response = userManager.getMyList(testUser);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getMyList()).hasSize(2);
        verify(guideService, times(1)).findByUser(testUser);
        verify(reviewService, times(1)).findRecommendedPlacesByUser(testUser);
    }

    @Test
    @DisplayName("저장 목록 조회 테스트")
    void getBookmarkedListTest() {
        // given
        when(bookmarkedGuideService.findBookmarkedGuidesByUser(any(User.class))).thenReturn(testGuides);
        when(bookmarkedPlaceService.findBookmarkedPlacesByUser(any(User.class))).thenReturn(testPlaces);
        when(bookmarkedGuideService.findBookmarkedGuideByUserAndGuide(any(User.class), any(Guide.class)))
                .thenReturn(testBookmarkedGuides.get(0));
        when(bookmarkedPlaceService.findBookmarkedPlaceByUserAndPlace(any(User.class), any(Place.class)))
                .thenReturn(testBookmarkedPlaces.get(0));

        // when
        BookmarkedListResponse response = userManager.getBookmarkedList(testUser);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getBookmarkedList()).hasSize(2);
        verify(bookmarkedGuideService, times(1)).findBookmarkedGuidesByUser(testUser);
        verify(bookmarkedPlaceService, times(1)).findBookmarkedPlacesByUser(testUser);
    }
} 