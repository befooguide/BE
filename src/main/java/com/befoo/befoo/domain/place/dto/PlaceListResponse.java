package com.befoo.befoo.domain.place.dto;

import com.befoo.befoo.global.dto.Response;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlaceListResponse implements Response {
    private List<PlaceResponse> places;

    public static PlaceListResponse from(List<PlaceResponse> placeResponses) {
        return PlaceListResponse.builder()
                .places(placeResponses)
                .build();
    }
} 