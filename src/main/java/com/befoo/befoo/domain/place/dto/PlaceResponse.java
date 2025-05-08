package com.befoo.befoo.domain.place.dto;

import com.befoo.befoo.domain.place.entity.Place;
import com.befoo.befoo.global.dto.Response;
import com.befoo.befoo.global.entity.BaseTime;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlaceResponse implements Response {
    private String placeId;
    private String placeName;
    private String description;
    private String image;
    private String url;
    private boolean isBookmarked;
    private LocalDateTime updatedAt;

    public static PlaceResponse from(Place place) {
        return PlaceResponse.builder()
                .placeId(place.getId())
                .placeName(place.getName())
                .description(place.getDescription())
                .image(place.getImage())
                .url(place.getUrl())
                .isBookmarked(false)
                .updatedAt(place.getReviews().stream()
                        .map(BaseTime::getUpdatedAt)
                        .max(LocalDateTime::compareTo)
                        .orElse(null))
                .build();
    }

    public PlaceResponse withBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
        return this;
    }
} 