package com.befoo.befoo.domain.exception;

public class GuideException extends RuntimeException {
    public GuideException(String guideId, String message) {
        super("Id: " + guideId + " - " + message);
    }

    public static GuideException notFound(String guideId) {
        return new GuideException(guideId, "가이드를 찾을 수 없습니다.");
    }

    public static GuideException invalidStatus(String guideId) {
        return new GuideException(guideId, "유효하지 않은 가이드 상태입니다.");
    }

    public static GuideException alreadyBooked(String guideId) {
        return new GuideException(guideId, "이미 예약된 가이드입니다.");
    }
} 