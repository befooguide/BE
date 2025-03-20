package com.befoo.befoo.domain.dto;

import com.befoo.befoo.domain.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private String role;
    private String username;

    public static UserDto from(User user) {
        return UserDto.builder()
                .role(user.getRole().name())
                .username(user.getUsername())
                .build();
    }
}