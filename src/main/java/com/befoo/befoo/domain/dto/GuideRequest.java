package com.befoo.befoo.domain.dto;

import com.befoo.befoo.domain.entity.enums.Allergy;
import com.befoo.befoo.domain.entity.enums.HealthCondition;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
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