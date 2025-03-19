package com.befoo.befoo.domain.dto;

import com.befoo.befoo.domain.entity.Place;
import com.befoo.befoo.global.dto.Response;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

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

    public static PlaceResponse from(Place place) {
        return PlaceResponse.builder()
                .placeId(place.getId())
                .placeName(place.getName())
                .description(place.getDescription())
                .image(place.getImage())
                .url(place.getUrl())
                .isBookmarked(false)
                .build();
    }

    public PlaceResponse withBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
        return this;
    }
} 