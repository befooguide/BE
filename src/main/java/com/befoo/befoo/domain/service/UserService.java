package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.dto.KakaoResponse;
import com.befoo.befoo.domain.dto.OAuth2Response;
import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.entity.enums.Allergy;
import com.befoo.befoo.domain.entity.enums.HealthCondition;
import com.befoo.befoo.domain.entity.enums.Role;
import com.befoo.befoo.domain.exception.UserException;
import com.befoo.befoo.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(OAuth2User oAuth2User) {
        OAuth2Response oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        
        return userRepository.findByUsername(username)
                .orElseGet(() -> {
                    User user = User.builder()
                            .username(username)
                            .email(oAuth2Response.getEmail())
                            .nickname(oAuth2Response.getName())
                            .role(Role.ROLE_USER)
                            .build();
                    return userRepository.save(user);
                });
    }

    public User getUserByUserId(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> UserException.notFound(userId));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> UserException.notFound(username));
    }

    public User updateHealthInfo(User user, List<HealthCondition> healthConditions, List<Allergy> allergies) {
        User updatedUser = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .image(user.getImage())
                .healthConditions(healthConditions)
                .allergies(allergies)
                .build();
        return userRepository.save(updatedUser);
    }
}
