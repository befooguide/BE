package com.befoo.befoo.domain.dto;

import com.befoo.befoo.domain.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private String role;
    private String name;
    private String username;

    public static UserDto from(User user) {
        return UserDto.builder()
                .role(user.getRole().name())
                .name(user.getNickname())
                .username(user.getUsername())
                .build();
    }
}