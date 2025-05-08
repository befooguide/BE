package com.befoo.befoo.domain.guide.dto;

import com.befoo.befoo.global.dto.Response;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GuideListResponse implements Response {
    private List<GuideResponse> guides;

    public static GuideListResponse from(List<GuideResponse> guides) {
        return GuideListResponse.builder()
                .guides(guides)
                .build();
    }
} 