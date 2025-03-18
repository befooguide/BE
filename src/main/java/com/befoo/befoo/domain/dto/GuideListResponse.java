package com.befoo.befoo.domain.dto;

import com.befoo.befoo.domain.entity.Guide;
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
public class GuideListResponse implements Response {
    private List<GuideResponse> guides;

    public static GuideListResponse from(List<Guide> guides) {
        List<GuideResponse> guideResponses = guides.stream()
                .map(GuideResponse::from)
                .collect(Collectors.toList());
        return GuideListResponse.builder()
                .guides(guideResponses)
                .build();
    }
} 