package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.dto.CustomOAuth2User;
import com.befoo.befoo.domain.dto.UserDto;
import com.befoo.befoo.domain.entity.User;
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

        log.info("OAuth2 로그인 진행 중 - 공급자: {}", userRequest.getClientRegistration().getRegistrationId());
        log.debug("OAuth2User 속성: {}", oAuth2User.getAttributes());
        
        User user = userService.createUser(oAuth2User);
        log.info("사용자 생성/조회 완료 - ID: {}, 이메일: {}", user.getId(), user.getEmail());
        
        UserDto userDto = UserDto.from(user);
        return new CustomOAuth2User(userDto);
    }
}