package com.befoo.befoo.domain.place.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewRequest {
    private String receipt;
    private int taste;
    private int menu;
    private int nutrition;
    private int health;
    private int totalScore;
    private String comment;
    private boolean recommend;
} 