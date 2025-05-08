package com.befoo.befoo.domain.place.dto;

import com.befoo.befoo.domain.place.entity.Review;
import com.befoo.befoo.global.dto.Response;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewResponse implements Response {
    private String reviewId;
    private String placeName;
    private Integer taste;
    private Integer menu;
    private Integer nutrition;
    private Integer health;
    private Integer totalScore;
    private String comment;
    private Boolean recommend;
    private PlaceInfo place;

    public static ReviewResponse from(Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getId())
                .placeName(review.getPlace().getName())
                .taste(review.getTaste())
                .menu(review.getMenu())
                .nutrition(review.getNutrition())
                .health(review.getHealth())
                .totalScore(review.getTotalScore())
                .comment(review.getComment())
                .recommend(review.getRecommend())
                .place(PlaceInfo.from(review.getPlace()))
                .build();
    }
}
