package com.befoo.befoo.domain.guide.dto;

import com.befoo.befoo.domain.user.entity.enums.Allergy;
import com.befoo.befoo.domain.user.entity.enums.HealthCondition;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.List;

@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GuideRequest {
    private String name;
    private String description;
    private List<String> placeIds;
    private List<HealthCondition> healthConditions;
    private List<Allergy> allergies;
} 