package com.befoo.befoo.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookmarkedListItem {
    private ContentType contentType;
    private GuideResponse guideResponse;
    private PlaceResponse placeResponse;
    private LocalDateTime guideUpdatedAt;
    private LocalDateTime placeUpdatedAt;
} 