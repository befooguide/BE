package com.befoo.befoo.global.jwt;

import com.befoo.befoo.domain.entity.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * 테스트 환경에서 JWT 토큰을 생성하기 위한 유틸리티 클래스
 * 테스트 목적으로만 사용되며, 실제 시크릿 키 대신 더미 값 사용
 */
@Component
public class TestJwtTokenProvider {

    private final SecretKey secretKey;
    private final long tokenValidityInMilliseconds;

    /**
     * 생성자 주입 방식으로 설정값을 받음
     * @param secretKeyString JWT 시크릿 키 (테스트용)
     * @param accessExpiration 토큰 유효 시간(밀리초)
     */
    public TestJwtTokenProvider(
            @Value("${jwt.secret-key}") String secretKeyString,
            @Value("${jwt.access-expiration}") long accessExpiration) {
        this.secretKey = createSecretKey(secretKeyString);
        this.tokenValidityInMilliseconds = accessExpiration;
    }
    
    /**
     * 비밀키 생성 - 테스트용
     * @param secret 비밀키 문자열
     * @return 암호화용 SecretKey 객체
     */
    private SecretKey createSecretKey(String secret) {
        return new SecretKeySpec(
            secret.getBytes(StandardCharsets.UTF_8), 
            Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    /**
     * 사용자 정보를 기반으로 JWT 토큰 생성
     * @param user 사용자 엔티티
     * @return 생성된 JWT 토큰
     */
    public String createToken(User user) {
        return createToken(user.getEmail(), user.getRole().name());
    }

    /**
     * 테스트용 특정 사용자 정보로 토큰 생성
     * 또는 단순히 더미 토큰을 생성하여 반환할 수도 있음
     * @param username 사용자 이메일
     * @param role 사용자 역할
     * @return 생성된 JWT 토큰
     */
    public String createToken(String username, String role) {
        // 실제 중요한 테스트가 아닌 경우 더미 토큰 생성 방식 사용 가능
        if (username.equals("dummy") || role.equals("dummy")) {
            return createDummyToken();
        }
        
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(validity)
                .signWith(secretKey)
                .compact();
    }
    
    /**
     * 테스트 전용 더미 토큰 생성 - 실제 JWT 검증이 필요없는 테스트에서 사용
     * 시크릿 키를 노출하지 않는 안전한 방식
     * @return 더미 JWT 토큰
     */
    public String createDummyToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." + 
               "eyJ1c2VybmFtZSI6InRlc3RAZXhhbXBsZS5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIn0." + 
               UUID.randomUUID().toString().replace("-", "");
    }
} 