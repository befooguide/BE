package com.befoo.befoo.domain.dto;

import com.befoo.befoo.domain.entity.Guide;
import com.befoo.befoo.global.dto.Response;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GuideResponse implements Response {
    private String guideId;
    private String name;
    private String description;
    private String username;
    private boolean isBookmarked;
    private List<PlaceInfo> places;
    private LocalDateTime updatedAt;

    public static GuideResponse from(Guide guide) {
        List<PlaceInfo> placeInfos = guide.getGuidePlaces().stream()
                .map(guidePlace -> PlaceInfo.from(guidePlace.getPlace()))
                .collect(Collectors.toList());

        return GuideResponse.builder()
                .guideId(guide.getId())
                .name(guide.getName())
                .description(guide.getDescription())
                .username(guide.getUser().getUsername())
                .isBookmarked(false)
                .places(placeInfos)
                .updatedAt(guide.getUpdatedAt())
                .build();
    }

    public GuideResponse withBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
        return this;
    }
} 