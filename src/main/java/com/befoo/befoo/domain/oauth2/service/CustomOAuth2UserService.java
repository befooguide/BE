package com.befoo.befoo.domain.oauth2.service;

import com.befoo.befoo.domain.oauth2.dto.CustomOAuth2User;
import com.befoo.befoo.domain.user.entity.User;
import com.befoo.befoo.domain.user.service.UserService;
import com.befoo.befoo.global.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        User user = userService.createUser(oAuth2User, provider);

        log.info("사용자 생성/조회 완료 - ID: {}, 이메일: {}", user.getId(), user.getEmail());

        TokenDto tokenDto = TokenDto.builder()
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();

        return new CustomOAuth2User(tokenDto);
    }
}