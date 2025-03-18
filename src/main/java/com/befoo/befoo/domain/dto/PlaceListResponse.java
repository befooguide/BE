package com.befoo.befoo.domain.dto;

import com.befoo.befoo.domain.entity.Place;
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
public class PlaceListResponse implements Response {
    private List<PlaceInfo> places;

    public static PlaceListResponse from(List<Place> places) {
        List<PlaceInfo> placeInfos = places.stream()
                .map(PlaceInfo::from)
                .collect(Collectors.toList());
        return PlaceListResponse.builder()
                .places(placeInfos)
                .build();
    }
} 