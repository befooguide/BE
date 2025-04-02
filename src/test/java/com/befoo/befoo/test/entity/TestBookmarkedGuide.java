package com.befoo.befoo.test.entity;

import com.befoo.befoo.domain.entity.BookmarkedGuide;
import com.befoo.befoo.domain.entity.Guide;
import com.befoo.befoo.domain.entity.User;
/**
 * 테스트에서 사용할 BookmarkedGuide 엔티티
 * 기본적인 테스트 데이터를 포함하고 있습니다.
 */
public class TestBookmarkedGuide {
    
    /**
     * 기본 테스트 북마크 가이드 생성
     * @param user 사용자
     * @param guide 가이드
     * @return BookmarkedGuide 엔티티
     */
    public static BookmarkedGuide createDefaultBookmarkedGuide(User user, Guide guide) {
        return BookmarkedGuide.builder()
                .id("test-bookmarked-guide-id")
                .user(user)
                .guide(guide)
                .build();
    }
} 