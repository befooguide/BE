package com.befoo.befoo.domain.oauth2.dto;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attribute;
    private final Map<String, Object> kakaoAccount;
    private final Map<String, Object> profile;

    @SuppressWarnings("unchecked")
    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
        this.kakaoAccount = attribute.containsKey("kakao_account") ? 
                            (Map<String, Object>) attribute.get("kakao_account") : 
                            Map.of();
        this.profile = kakaoAccount.containsKey("profile") ? 
                       (Map<String, Object>) kakaoAccount.get("profile") : 
                       Map.of();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return kakaoAccount.containsKey("email") ? 
               kakaoAccount.get("email").toString() : 
               "email";
    }

    @Override
    public String getName() {
        return profile.containsKey("nickname") ? 
               profile.get("nickname").toString() : 
               "카카오";
    }

    @Override
    public String getImage() {
        return "";
    }
}