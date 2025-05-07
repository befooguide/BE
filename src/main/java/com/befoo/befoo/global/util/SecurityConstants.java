package com.befoo.befoo.global.util;

import java.util.Arrays;
import java.util.List;

public class SecurityConstants {
    // 인증이 필요 없는 URL 패턴
    public static final List<String> PUBLIC_URLS = Arrays.asList(
            "/api/users/exists-username",
            "/api/guides",
            "/api/guides/**"
    );

    // URL 패턴이 일치하는지 확인하는 메서드
    public static boolean isPublicUrl(String path) {
        return PUBLIC_URLS.stream()
                .anyMatch(pattern -> path.matches(pattern.replace("**", ".*")));
    }
} 