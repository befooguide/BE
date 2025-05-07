package com.befoo.befoo.global.jwt;

import lombok.Builder;

public record TokenDto(
        String username,
        String role
) {
    @Builder
    public static TokenDto create(String username, String role) {
        return new TokenDto(username, role);
    }
}
