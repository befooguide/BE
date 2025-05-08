package com.befoo.befoo.domain.user.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum HealthCondition {
    DIABETES("당뇨"),
    HYPERTENSION("고혈압"),
    HYPERLIPIDEMIA("고지혈증"),
    HEART_DISEASE("심장병"),
    GASTRITIS("위염-위장장애"),
    KIDNEY_DISEASE("신장 질환"),
    LIVER_DISEASE("간 질환"),
    CANCER("암"),
    OSTEOPOROSIS("골다공증"),
    DIGESTIVE_DISORDER("소화기 질환");

    private final String description;

    @JsonCreator
    public static HealthCondition fromDescription(String description) {
        return Arrays.stream(values())
                .filter(constant -> constant.description.equals(description))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String serializer(){
        return description;
    }
} 