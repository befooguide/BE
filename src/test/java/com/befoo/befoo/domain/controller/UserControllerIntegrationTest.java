package com.befoo.befoo.domain.controller;

import com.befoo.befoo.domain.dto.BookmarkedListResponse;
import com.befoo.befoo.domain.dto.CustomUserDetails;
import com.befoo.befoo.domain.dto.MyListResponse;
import com.befoo.befoo.domain.dto.UserProfileRequest;
import com.befoo.befoo.domain.dto.UserProfileResponse;
import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.entity.enums.Allergy;
import com.befoo.befoo.domain.entity.enums.HealthCondition;
import com.befoo.befoo.domain.service.UserManager;
import com.befoo.befoo.global.util.ApiDocumentationUtil;
import com.befoo.befoo.test.entity.TestUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserController 통합 테스트
 * JWT 인증을 사용하는 방식으로 테스트
 */
@ExtendWith(MockitoExtension.class)
class UserControllerIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerIntegrationTest.class);
    
    private MockMvc mockMvc;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserManager userManager;
    
    @InjectMocks
    private UserController userController;

    private UserProfileResponse profileResponse;
    private User testUser;
    private CustomUserDetails userDetails;
    private String authToken;

    @BeforeEach
    void setUp() {
        authToken = createDummyJwtToken();
        initializeTestData();
        configureMockMvc();
    }
    
    /**
     * 시크릿 키를 노출하지 않는 더미 JWT 토큰 생성
     */
    private String createDummyJwtToken() {
        // 테스트용 더미 토큰 - 실제 시크릿 키를 사용하지 않음
        // JWT 형식을 갖춘 유효한 형태의 더미 토큰 생성
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." + 
               "eyJ1c2VybmFtZSI6InRlc3RAZXhhbXBsZS5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIn0." + 
               UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 테스트 데이터 초기화
     */
    private void initializeTestData() {
        testUser = TestUser.createDefaultUser();
        userDetails = new CustomUserDetails(testUser);
        profileResponse = UserProfileResponse.from(testUser);
    }
    
    /**
     * MockMvc 구성
     */
    private void configureMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setCustomArgumentResolvers(new TestAuthArgumentResolver(userDetails))
                .alwaysDo(ApiDocumentationUtil.documentRequest())
                .alwaysDo(ApiDocumentationUtil.documentResponse())
                .build();
    }

    @Test
    @DisplayName("[GET] 프로필 조회 API 테스트")
    void getProfileApiTest() throws Exception {
        // given
        when(userManager.getProfile(any(User.class))).thenReturn(profileResponse);
        
        // when & then - JWT 토큰을 헤더에 추가
        mockMvc.perform(get("/api/users/profile")
                .header("Authorization", "Bearer " + authToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("프로필 조회 성공"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.user_id").value("test-user-id"))
                .andExpect(jsonPath("$.data.username").value("테스트사용자"))
                .andExpect(jsonPath("$.data.health_conditions[0]").value("당뇨"))
                .andExpect(jsonPath("$.data.health_conditions[1]").value("고혈압"))
                .andExpect(jsonPath("$.data.allergies[0]").value("계란"))
                .andExpect(jsonPath("$.data.allergies[1]").value("밀"));
    }

    @Test
    @DisplayName("[PUT] 프로필 수정 API 테스트")
    void putProfileApiTest() throws Exception {
        // given
        UserProfileRequest request = createUserProfileRequest();
        UserProfileResponse updatedResponse = createUpdatedProfileResponse();
        
        when(userManager.putProfile(any(User.class), any(UserProfileRequest.class))).thenReturn(updatedResponse);
        
        // when & then - JWT 토큰을 헤더에 추가
        mockMvc.perform(put("/api/users/profile")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("프로필 수정 성공"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.user_id").value("test-user-id"))
                .andExpect(jsonPath("$.data.username").value("테스트사용자"))
                .andExpect(jsonPath("$.data.health_conditions[0]").value("암"))
                .andExpect(jsonPath("$.data.health_conditions[1]").value("심장병"))
                .andExpect(jsonPath("$.data.health_conditions[2]").value("골다공증"))
                .andExpect(jsonPath("$.data.allergies[0]").value("닭고기"))
                .andExpect(jsonPath("$.data.allergies[1]").value("소고기"))
                .andExpect(jsonPath("$.data.allergies[2]").value("돼지고기"));
    }
    
    /**
     * 프로필 수정 요청 객체 생성
     */
    private UserProfileRequest createUserProfileRequest() {
        UserProfileRequest request = new UserProfileRequest();
        request.setHealthConditions(Arrays.asList(HealthCondition.CANCER, HealthCondition.HEART_DISEASE, HealthCondition.OSTEOPOROSIS));
        request.setAllergies(Arrays.asList(Allergy.CHICKEN, Allergy.BEEF, Allergy.PORK));
        return request;
    }
    
    /**
     * 업데이트된 프로필 응답 객체 생성
     */
    private UserProfileResponse createUpdatedProfileResponse() {
        return UserProfileResponse.builder()
                .userId("test-user-id")
                .username("테스트사용자")
                .healthConditions(Arrays.asList("암", "심장병", "골다공증"))
                .allergies(Arrays.asList("닭고기", "소고기", "돼지고기"))
                .build();
    }

    @Test
    @DisplayName("[GET] 나만의 목록 조회 API 테스트")
    void getMyListApiTest() throws Exception {
        // given
        MyListResponse mockResponse = MyListResponse.builder()
                .myList(new ArrayList<>())
                .build();
                
        when(userManager.getMyList(any(User.class))).thenReturn(mockResponse);
        
        // when & then - JWT 토큰을 헤더에 추가
        mockMvc.perform(get("/api/users/my-list")
                .header("Authorization", "Bearer " + authToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("나만의 목록 조회 성공"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.my_list").isArray());
    }

    @Test
    @DisplayName("[GET] 저장 목록 조회 API 테스트")
    void getBookmarkedListApiTest() throws Exception {
        // given
        BookmarkedListResponse mockResponse = BookmarkedListResponse.builder()
                .bookmarkedList(new ArrayList<>())
                .build();
                
        when(userManager.getBookmarkedList(any(User.class))).thenReturn(mockResponse);
        
        // when & then - JWT 토큰을 헤더에 추가
        mockMvc.perform(get("/api/users/bookmarked")
                .header("Authorization", "Bearer " + authToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("저장 목록 조회 성공"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.bookmarked_list").isArray());
    }
}