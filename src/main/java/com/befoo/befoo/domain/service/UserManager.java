package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.dto.*;
import com.befoo.befoo.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManager {
    private final GuideManager guideManager;
    private final PlaceManager placeManager;
    private final UserService userService;
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
        GuideListResponse guideListResponse = guideManager.getMyGuides(user);
        myList.addAll(guideListResponse.getGuides().stream()
                .map(guideResponse -> MyListItem.builder()
                        .contentType(ContentType.GUIDE)
                        .guideResponse(guideResponse)
                        .build())
                .collect(Collectors.toList()));

        // 추천 식당 목록 조회
        PlaceListResponse placeListResponse = placeManager.getMyRecommendedPlaces(user);
        myList.addAll(placeListResponse.getPlaces().stream()
                .map(placeResponse -> MyListItem.builder()
                        .contentType(ContentType.PLACE)
                        .placeResponse(placeResponse)
                        .build())
                .collect(Collectors.toList()));

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
        GuideListResponse guideListResponse = guideManager.getBookmarkedGuides(user);
        bookmarkedList.addAll(guideListResponse.getGuides().stream()
                .map(guideResponse -> BookmarkedListItem.builder()
                        .contentType(ContentType.GUIDE)
                        .guideResponse(guideResponse)
                        .guideUpdatedAt(guideResponse.getUpdatedAt())
                        .build())
                .collect(Collectors.toList()));

        // 저장된 식당 목록 조회
        PlaceListResponse placeListResponse = placeManager.getMyBookmarkedPlaces(user);
        bookmarkedList.addAll(placeListResponse.getPlaces().stream()
                .map(placeResponse -> BookmarkedListItem.builder()
                        .contentType(ContentType.PLACE)
                        .placeResponse(placeResponse)
                        .placeUpdatedAt(placeResponse.getUpdatedAt())
                        .build())
                .collect(Collectors.toList()));

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
