package com.befoo.befoo.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MyListItem {
    private ContentType contentType;
    private GuideResponse guideResponse;
    private PlaceResponse placeResponse;
} 