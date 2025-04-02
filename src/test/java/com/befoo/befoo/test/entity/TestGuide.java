package com.befoo.befoo.test.entity;

import com.befoo.befoo.domain.entity.Guide;
import com.befoo.befoo.domain.entity.User;
import java.time.LocalDateTime;

/**
 * 테스트에서 사용할 Guide 엔티티
 * 기본적인 테스트 데이터를 포함하고 있습니다.
 */
public class TestGuide {
    
    /**
     * 기본 테스트 가이드 생성
     * @param author 작성자
     * @return Guide 엔티티
     */
    public static Guide createDefaultGuide(User author) {
        return Guide.builder()
                .id("test-guide-id")
                .name("테스트 가이드 제목")
                .description("테스트 가이드 내용")
                .user(author)
                .build();
    }
} 