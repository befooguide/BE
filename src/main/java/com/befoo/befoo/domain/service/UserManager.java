package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.dto.*;
import com.befoo.befoo.domain.entity.BookmarkedPlace;
import com.befoo.befoo.domain.entity.Guide;
import com.befoo.befoo.domain.entity.Place;
import com.befoo.befoo.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManager {
    private final UserService userService;
    private final GuideService guideService;
    private final ReviewService reviewService;
    private final BookmarkedGuideService bookmarkedGuideService;
    private final BookmarkedPlaceService bookmarkedPlaceService;

    // API: 프로필 조회
    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(User user) {
        return UserProfileResponse.from(user);
    }

    // API: 프로필 수정
    @Transactional
    public UserProfileResponse putProfile(User user, UserProfileRequest request) {
        User updatedUser = userService.updateProfile(user, request);
        return UserProfileResponse.from(updatedUser);
    }

    // API: 나만의 목록 조회
    @Transactional(readOnly = true)
    public MyListResponse getMyList(User user) {
        List<MyListItem> myList = new ArrayList<>();

        // 가이드 목록 조회
        List<Guide> guides = guideService.findByUser(user);
        List<GuideResponse> guideResponses = guides.stream()
                .map(guide -> GuideResponse.from(guide)
                        .withBookmarked(bookmarkedGuideService.isBookmarked(user, guide)))
                .toList();
        myList.addAll(guideResponses.stream()
                .map(guideResponse -> MyListItem.builder()
                        .contentType(ContentType.GUIDE)
                        .guideResponse(guideResponse)
                        .build())
                .toList());

        // 추천 식당 목록 조회
        List<Place> places = reviewService.findRecommendedPlacesByUser(user);
        List<PlaceResponse> placeResponses = places.stream()
                .map(place -> PlaceResponse.from(place)
                        .withBookmarked(bookmarkedPlaceService.isBookmarked(user, place)))
                .toList();
        myList.addAll(placeResponses.stream()
                .map(placeResponse -> MyListItem.builder()
                        .contentType(ContentType.PLACE)
                        .placeResponse(placeResponse)
                        .build())
                .toList());

        // updatedAt 기준으로 정렬
        myList.sort(Comparator.comparing(item -> {
            if (item.getContentType() == ContentType.GUIDE) {
                return item.getGuideResponse().getUpdatedAt();
            } else {
                return item.getPlaceResponse().getUpdatedAt();
            }
        }, Comparator.nullsLast(Comparator.reverseOrder())));

        return MyListResponse.from(myList);
    }

    // API: 저장 목록 조회
    @Transactional(readOnly = true)
    public BookmarkedListResponse getBookmarkedList(User user) {
        List<BookmarkedListItem> bookmarkedList = new ArrayList<>();

        // 저장된 가이드 목록 조회
        List<Guide> guides = bookmarkedGuideService.findBookmarkedGuidesByUser(user);
        List<GuideResponse> guideResponses = guides.stream()
                .map(guide -> GuideResponse.from(guide)
                        .withBookmarked(true))
                .toList();

        bookmarkedList.addAll(guideResponses.stream()
                .map(guideResponse -> BookmarkedListItem.builder()
                        .contentType(ContentType.GUIDE)
                        .guideResponse(guideResponse)
                        .guideUpdatedAt(guideResponse.getUpdatedAt())
                        .build())
                .toList());

        // 저장된 식당 목록 조회
        List<Place> places = bookmarkedPlaceService.getBookmarkedPlaces(user).stream()
                .map(BookmarkedPlace::getPlace)
                .toList();
        List<PlaceResponse> placeResponses = places.stream()
                .map(place -> PlaceResponse.from(place)
                        .withBookmarked(true))
                .toList();
        bookmarkedList.addAll(placeResponses.stream()
                .map(placeResponse -> BookmarkedListItem.builder()
                        .contentType(ContentType.PLACE)
                        .placeResponse(placeResponse)
                        .placeUpdatedAt(placeResponse.getUpdatedAt())
                        .build())
                .toList());

        // updatedAt 기준으로 정렬
        bookmarkedList.sort(Comparator.comparing(item -> {
            if (item.getContentType() == ContentType.GUIDE) {
                return item.getGuideUpdatedAt();
            } else {
                return item.getPlaceUpdatedAt();
            }
        }, Comparator.nullsLast(Comparator.reverseOrder())));

        return BookmarkedListResponse.from(bookmarkedList);
    }
}
