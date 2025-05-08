package com.befoo.befoo.domain.user.dto;

import com.befoo.befoo.domain.user.entity.enums.Allergy;
import com.befoo.befoo.domain.user.entity.enums.HealthCondition;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserProfileRequest {
    private String username;
    private List<HealthCondition> healthConditions;
    private List<Allergy> allergies;
}