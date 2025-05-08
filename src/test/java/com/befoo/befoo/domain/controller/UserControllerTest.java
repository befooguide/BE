package com.befoo.befoo.domain.controller;

import com.befoo.befoo.domain.user.dto.BookmarkedListResponse;
import com.befoo.befoo.domain.user.dto.MyListResponse;
import com.befoo.befoo.domain.user.dto.UserProfileRequest;
import com.befoo.befoo.domain.user.dto.UserProfileResponse;
import com.befoo.befoo.domain.user.entity.User;
import com.befoo.befoo.domain.user.entity.enums.Role;
import com.befoo.befoo.domain.user.controller.UserController;
import com.befoo.befoo.domain.user.service.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        
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

    @Test
    @DisplayName("닉네임 중복 확인 - 사용 가능한 닉네임")
    void checkUsername_Available() throws Exception {
        // given
        String username = "testUser";
        when(userManager.isUsernameAvailable(username)).thenReturn(true);

        // when & then
        mockMvc.perform(get("/api/users/exists-username")
                .param("username", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.message").value("사용 가능한 닉네임입니다."));
    }

    @Test
    @DisplayName("닉네임 중복 확인 - 이미 사용 중인 닉네임")
    void checkUsername_AlreadyExists() throws Exception {
        // given
        String username = "existingUser";
        when(userManager.isUsernameAvailable(username)).thenReturn(false);

        // when & then
        mockMvc.perform(get("/api/users/exists-username")
                .param("username", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(false))
                .andExpect(jsonPath("$.message").value("이미 사용 중인 닉네임입니다."));
    }

    @Test
    @DisplayName("닉네임 중복 확인 - 닉네임 파라미터 누락")
    void checkUsername_MissingUsername() throws Exception {
        // when & then
        mockMvc.perform(get("/api/users/exists-username"))
                .andExpect(status().isBadRequest());
    }
} 