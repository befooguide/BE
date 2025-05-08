package com.befoo.befoo.domain.user.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Allergy {
    DAIRY("유제품"),
    EGG("계란"),
    WHEAT("밀"),
    NUTS("땅콩/견과류"),
    SEAFOOD("해산물"),
    CHICKEN("닭고기"),
    BEEF("소고기"),
    PORK("돼지고기");

    private final String description;

    @JsonCreator
    public static Allergy fromDescription(String description) {
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