package com.befoo.befoo.domain.place.exception;

public class ReviewException extends RuntimeException {
    public ReviewException(String reviewId, String message) {
        super("Id: " + reviewId + " - " + message);
    }

    public static ReviewException notFound(String reviewId) {
        return new ReviewException(reviewId, "리뷰를 찾을 수 없습니다.");
    }

    public static ReviewException invalidScore(String reviewId) {
        return new ReviewException(reviewId, "유효하지 않은 점수입니다.");
    }

    public static ReviewException duplicateReview(String reviewId) {
        return new ReviewException(reviewId, "이미 작성한 리뷰가 있습니다.");
    }

    public static ReviewException notAuthorized(String reviewId) {
        return new ReviewException(reviewId, "리뷰 작성자만 수정할 수 있습니다.");
    }
} 