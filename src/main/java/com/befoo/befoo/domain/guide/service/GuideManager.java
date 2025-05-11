package com.befoo.befoo.domain.guide.service;

import com.befoo.befoo.domain.guide.dto.GuideListResponse;
import com.befoo.befoo.domain.guide.dto.GuideRequest;
import com.befoo.befoo.domain.guide.dto.GuideResponse;
import com.befoo.befoo.domain.guide.entity.Guide;
import com.befoo.befoo.domain.place.entity.Place;
import com.befoo.befoo.domain.place.entity.Review;
import com.befoo.befoo.domain.place.service.PlaceService;
import com.befoo.befoo.domain.place.service.ReviewService;
import com.befoo.befoo.domain.user.entity.User;
import com.befoo.befoo.domain.user.service.UserService;
import com.befoo.befoo.domain.guide.exception.GuideException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuideManager {
    private final UserService userService;
    private final GuideService guideService;
    private final PlaceService placeService;
    private final ReviewService reviewService;
    private final BookmarkedGuideService bookmarkedGuideService;

    // API: 가이드 생성
    @Transactional
    public GuideResponse createGuide(String username, GuideRequest request) {
        User user = userService.getUserByUsername(username);
        List<Place> places = request.getPlaceIds().stream()
                .map(placeService::findById)
                .collect(Collectors.toList());
        validatePlacesHaveUserReview(user, places);
        Guide guide = guideService.createGuide(user, request, places);
        return GuideResponse.from(guide)
                .withBookmarked(bookmarkedGuideService.isBookmarked(user, guide));
    }

    // API: 가이드 수정
    @Transactional
    public GuideResponse updateGuide(String guideId, String username, GuideRequest request) {
        User user = userService.getUserByUsername(username);
        Guide guide = guideService.findById(guideId);
        guideService.validateGuideBelongsToUser(guide, user.getId());

        List<Place> places = request.getPlaceIds().stream()
                .map(placeService::findById)
                .collect(Collectors.toList());
        validatePlacesHaveUserReview(user, places);

        Guide updatedGuide = guideService.updateGuide(guideId, request, places);
        return GuideResponse.from(updatedGuide)
                .withBookmarked(bookmarkedGuideService.isBookmarked(user, updatedGuide));
    }

    // API: 가이드 삭제
    @Transactional
    public void deleteGuide(String guideId, String username) {
        User user = userService.getUserByUsername(username);
        Guide guide = guideService.findById(guideId);
        guideService.validateGuideBelongsToUser(guide, user.getId());
        guideService.deleteGuide(guideId);
    }

    // API: 가이드 목록 조회
    @Transactional(readOnly = true)
    public GuideListResponse getGuides(String username) {
        User user = userService.getUserByUsername(username);
        List<Guide> guides = guideService.findAll();
        List<GuideResponse> guideResponses = guides.stream()
                .map(guide -> GuideResponse.from(guide)
                        .withBookmarked(bookmarkedGuideService.isBookmarked(user, guide)))
                .collect(Collectors.toList());

        return GuideListResponse.from(guideResponses);
    }

    // API: 가이드 상세 조회
    @Transactional(readOnly = true)
    public GuideResponse getGuideDetail(String guideId, String username) {
        User user = userService.getUserByUsername(username);
        Guide guide = guideService.findById(guideId);
        return GuideResponse.from(guide)
                .withBookmarked(bookmarkedGuideService.isBookmarked(user, guide));
    }

    // API: 나만의 가이드 목록 조회
    @Transactional(readOnly = true)
    public GuideListResponse getMyGuides(String username) {
        User user = userService.getUserByUsername(username);
        List<Guide> guides = guideService.findByUser(user);
        List<GuideResponse> guideResponses = guides.stream()
                .map(guide -> GuideResponse.from(guide)
                        .withBookmarked(bookmarkedGuideService.isBookmarked(user, guide)))
                .toList();

        return GuideListResponse.from(guideResponses);
    }

    // API: 가이드 저장
    @Transactional
    public GuideResponse bookmarkGuide(String guideId, String username) {
        User user = userService.getUserByUsername(username);
        Guide guide = guideService.findById(guideId);
        bookmarkedGuideService.createBookmarkedGuide(user, guide);
        return GuideResponse.from(guide)
                .withBookmarked(true);
    }

    // API: 저장 가이드 목록 조회
    @Transactional(readOnly = true)
    public GuideListResponse getBookmarkedGuides(String username) {
        User user = userService.getUserByUsername(username);
        List<Guide> guides = bookmarkedGuideService.findBookmarkedGuidesByUser(user);
        List<GuideResponse> guideResponses = guides.stream()
                .map(guide -> GuideResponse.from(guide)
                        .withBookmarked(true))
                .toList();

        return GuideListResponse.from(guideResponses);
    }

    // API: 가이드 저장 취소
    @Transactional
    public void unbookmarkGuide(String guideId, String username) {
        User user = userService.getUserByUsername(username);
        Guide guide = guideService.findById(guideId);
        bookmarkedGuideService.deleteBookmarkedGuide(user, guide);
    }

    private void validatePlacesHaveUserReview(User user, List<Place> places) {
        for (Place place : places) {
            List<Review> reviews = reviewService.findReviewsByPlaceId(place.getId());
            boolean hasUserReview = reviews.stream()
                    .anyMatch(review -> review.getUser().getId().equals(user.getId()));
            if (!hasUserReview) {
                throw GuideException.invalidPlace(place.getId());
            }
        }
    }
}
