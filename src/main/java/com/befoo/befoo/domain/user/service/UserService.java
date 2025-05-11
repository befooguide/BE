package com.befoo.befoo.domain.user.service;

import com.befoo.befoo.domain.oauth2.dto.KakaoResponse;
import com.befoo.befoo.domain.oauth2.dto.OAuth2Response;
import com.befoo.befoo.domain.user.dto.UserProfileRequest;
import com.befoo.befoo.domain.user.entity.User;
import com.befoo.befoo.domain.user.entity.enums.Role;
import com.befoo.befoo.domain.user.exception.UserException;
import com.befoo.befoo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(OAuth2User oAuth2User, String provider) {
        OAuth2Response oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        String username = provider + "-" + oAuth2Response.getProviderId();

        log.info("username: {}", username);

        if (userRepository.existsByUsername(username)) {
            return findUserByUsername(username);
        }

        User user = User.builder()
                .username(username)
                .role(Role.ROLE_USER)
                .email(oAuth2Response.getEmail())
                .nickname(oAuth2Response.getName())
                .image(oAuth2Response.getImage())
                .build();
        return userRepository.save(user);
    }

    public User findUserByUserId(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> UserException.notFound(userId));
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> UserException.notFound(username));
    }

    public User updateProfile(User user, UserProfileRequest request) {
        user.updateProfile(request);
        return userRepository.save(user);
    }

    public boolean isNicknameAvailable(String nickname) {
        return !userRepository.existsByNickname(nickname);
    }
}
