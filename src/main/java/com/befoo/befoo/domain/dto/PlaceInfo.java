package com.befoo.befoo.domain.dto;

import com.befoo.befoo.domain.entity.Place;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlaceInfo {
    private String placeName;
    private String description;
    private String image;
    private String url;
    private String address;
    private String operatingHours;
    private Boolean isBookmarked;

    public static PlaceInfo from(Place place) {
        return PlaceInfo.builder()
                .placeName(place.getName())
                .description(place.getDescription())
                .image(place.getImage())
                .url(place.getUrl())
                // .address(place.getAddress())
                // .operatingHours(place.getOperatingHours())
                .isBookmarked(false)
                .build();
    }
}
