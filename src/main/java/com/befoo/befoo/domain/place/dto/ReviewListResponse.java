package com.befoo.befoo.domain.place.dto;

import com.befoo.befoo.domain.place.entity.Review;
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
public class ReviewListResponse implements Response {
    private List<ReviewResponse> reviews;

    public static ReviewListResponse from(List<Review> reviews) {
        List<ReviewResponse> reviewResponses = reviews.stream()
                .map(ReviewResponse::from)
                .collect(Collectors.toList());
        return ReviewListResponse.builder()
                .reviews(reviewResponses)
                .build();
    }
} 