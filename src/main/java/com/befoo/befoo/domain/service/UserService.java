package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.dto.KakaoResponse;
import com.befoo.befoo.domain.dto.OAuth2Response;
import com.befoo.befoo.domain.dto.UserProfileRequest;
import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.entity.enums.Role;
import com.befoo.befoo.domain.exception.UserException;
import com.befoo.befoo.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(OAuth2User oAuth2User) {
        OAuth2Response oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        String username = oAuth2Response.getName()+" "+oAuth2Response.getProviderId();
        if (userRepository.findByUsername(username).isPresent()) {
            throw UserException.alreadyExists(username);
        }
        User user = User.builder()
                .username(username)
                .email(oAuth2Response.getEmail())
                .image(oAuth2Response.getImage())
                .role(Role.ROLE_USER)
                .build();
        return userRepository.save(user);
    }

    public User getUserByUserId(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> UserException.notFound(userId));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> UserException.notFound(username));
    }

    public User updateProfile(User user, UserProfileRequest request) {
        user.updateProfile(request);
        return userRepository.save(user);
    }

    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }
}
