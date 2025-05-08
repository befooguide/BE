package com.befoo.befoo.global.config;

import com.befoo.befoo.domain.oauth2.dto.CustomUserDetails;
import com.befoo.befoo.domain.user.entity.User;
import com.befoo.befoo.global.jwt.JwtUtil;
import com.befoo.befoo.global.jwt.TestJwtTokenProvider;
import com.befoo.befoo.test.entity.TestUser;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 테스트 환경에서 사용할 보안 설정
 * 실제 보안 설정을 오버라이드하여 테스트를 용이하게 합니다.
 * 테스트 환경에서는 보안이 완화된 설정을 사용하되, 실제 배포 환경과는 다르게 구성합니다.
 */
@TestConfiguration
public class TestSecurityConfig {

    private final String secretKey;
    private final long accessExpiration;

    /**
     * 생성자 주입 방식으로 설정값을 받음
     * 테스트 환경 전용 설정 값을 사용 (실제 운영 환경의 값과 달라야 함)
     * @param secretKey JWT 시크릿 키 (테스트용)
     * @param accessExpiration 토큰 유효 시간(밀리초)
     */
    public TestSecurityConfig(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.access-expiration}") long accessExpiration) {
        this.secretKey = secretKey;
        this.accessExpiration = accessExpiration;
    }

    /**
     * 테스트용 JWT 토큰 프로바이더 빈 생성
     * 테스트에서 토큰 생성에 사용됨
     * @return TestJwtTokenProvider 인스턴스
     */
    @Bean
    public TestJwtTokenProvider testJwtTokenProvider() {
        return new TestJwtTokenProvider(secretKey, accessExpiration);
    }

    /**
     * 테스트용 JwtUtil 빈 생성
     * 실제 애플리케이션의 JwtUtil을 대체
     * 이 빈이 실제 JwtUtil 대신 주입됨 (@Primary)
     * @return JwtUtil 인스턴스
     */
    @Bean
    @Primary
    public JwtUtil jwtUtil() {
        // 테스트 환경에서만 사용되는 설정이므로, 운영 환경의 실제 키와 달라야 함
        return new JwtUtil(secretKey);
    }

    /**
     * 테스트용 SecurityFilterChain 설정
     * 실제 애플리케이션의 보안 설정을 무시하고 테스트에 적합한 설정을 제공
     * 모든 요청에 대해 인증을 수행하지 않음
     * @param http HttpSecurity 인스턴스
     * @return SecurityFilterChain 인스턴스
     * @throws Exception 설정 중 예외 발생 시
     */
    @Bean
    @Primary
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> 
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> 
                    authorize.anyRequest().permitAll())
                .addFilterBefore(new TestAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Primary
    public UserDetailsService testUserDetailsService() {
        return username -> {
            User testUser = TestUser.createDefaultUser();
            return new CustomUserDetails(testUser);
        };
    }

    /**
     * 테스트 환경에서 모든 요청에 인증 정보를 제공하는 필터
     */
    private static class TestAuthenticationFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
                throws ServletException, IOException {
            
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }
            
            User testUser = TestUser.createDefaultUser();
            
            CustomUserDetails userDetails = new CustomUserDetails(testUser);
            UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }
} 