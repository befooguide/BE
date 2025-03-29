package com.befoo.befoo.config;

import com.befoo.befoo.domain.dto.CustomUserDetails;
import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.entity.enums.Role;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 테스트 환경에서 모든 요청에 인증 정보를 제공하는 필터
 */
public class TestAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        // 이미 인증 정보가 있으면 그대로 진행
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // 테스트용 사용자 생성
        User testUser = User.builder()
                .username("테스트사용자")
                .email("test@example.com")
                .role(Role.ROLE_USER)
                .healthConditions(new ArrayList<>())
                .allergies(new ArrayList<>())
                .build();
        
        CustomUserDetails userDetails = new CustomUserDetails(testUser);
        
        // 인증 정보 설정
        UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        
        // SecurityContext에 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        filterChain.doFilter(request, response);
    }
} 