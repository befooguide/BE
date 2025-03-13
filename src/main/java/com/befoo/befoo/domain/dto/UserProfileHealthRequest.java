package com.befoo.befoo.domain.dto;

import com.befoo.befoo.domain.entity.enums.Allergy;
import com.befoo.befoo.domain.entity.enums.HealthCondition;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserProfileHealthRequest {
    private List<HealthCondition> healthConditions;
    private List<Allergy> allergies;
}