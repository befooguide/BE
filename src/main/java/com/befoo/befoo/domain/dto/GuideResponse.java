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
public class GuideResponse implements Response {
    private String guideId;
    private String name;
    private String description;
    private String userNickname;
    private boolean isBookmarked;
    private List<PlaceInfo> places;

    public static GuideResponse from(Guide guide) {
        List<PlaceInfo> placeInfos = guide.getGuidePlaces().stream()
                .map(guidePlace -> PlaceInfo.from(guidePlace.getPlace()))
                .collect(Collectors.toList());

        return GuideResponse.builder()
                .guideId(guide.getId())
                .name(guide.getName())
                .description(guide.getDescription())
                .userNickname(guide.getUser().getNickname())
                .isBookmarked(false)
                .places(placeInfos)
                .build();
    }

    public GuideResponse withBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
        return this;
    }
} 