package com.befoo.befoo.domain.exception;

public class PlaceException extends RuntimeException {
    public PlaceException(String placeId, String message) {
        super("Id: " + placeId + " - " + message);
    }

    public static PlaceException notFound(String placeId) {
        return new PlaceException(placeId, "장소를 찾을 수 없습니다.");
    }

    public static PlaceException invalidReview(String placeId) {
        return new PlaceException(placeId, "해당 장소의 리뷰가 아닙니다.");
    }

    public static PlaceException alreadyBookmarked(String placeId) {
        return new PlaceException(placeId, "이미 저장된 장소입니다.");
    }

    public static PlaceException notBookmarked(String placeId) {
        return new PlaceException(placeId, "저장되지 않은 장소입니다.");
    }
} 