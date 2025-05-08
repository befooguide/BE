package com.befoo.befoo.domain.user.dto;

import com.befoo.befoo.global.entity.enums.ContentType;
import com.befoo.befoo.domain.guide.dto.GuideResponse;
import com.befoo.befoo.domain.place.dto.PlaceResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MyListItem {
    private ContentType contentType;
    private GuideResponse guideResponse;
    private PlaceResponse placeResponse;
    private LocalDateTime updatedAt;
} 