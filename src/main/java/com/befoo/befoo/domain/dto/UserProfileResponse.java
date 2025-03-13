package com.befoo.befoo.domain.dto;

import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.entity.enums.Allergy;
import com.befoo.befoo.domain.entity.enums.HealthCondition;
import com.befoo.befoo.global.dto.Response;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserProfileResponse implements Response {
    private String userId;
    private String nickname;
    private List<String> healthConditions;
    private List<String> allergies;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .userId(user.getId())
                .nickname(user.getName())
                .healthConditions(user.getHealthConditions() != null ? 
                        user.getHealthConditions().stream()
                        .map(HealthCondition::getDescription)
                        .collect(Collectors.toList()) : 
                        List.of())
                .allergies(user.getAllergies() != null ? 
                        user.getAllergies().stream()
                        .map(Allergy::getDescription)
                        .collect(Collectors.toList()) : 
                        List.of())
                .build();
    }
} 