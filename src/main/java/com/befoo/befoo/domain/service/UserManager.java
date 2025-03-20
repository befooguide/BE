package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.dto.UserProfileRequest;
import com.befoo.befoo.domain.dto.UserProfileResponse;
import com.befoo.befoo.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserManager {
    private final UserService userService;

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(User user) {
        return UserProfileResponse.from(user);
    }

    @Transactional
    public UserProfileResponse putProfile(User user, UserProfileRequest request) {
        User updatedUser = userService.updateProfile(user, request);
        return UserProfileResponse.from(updatedUser);
    }
}
