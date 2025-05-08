package com.befoo.befoo.domain.user.service;

import com.befoo.befoo.domain.guide.dto.GuideResponse;
import com.befoo.befoo.domain.guide.entity.BookmarkedGuide;
import com.befoo.befoo.domain.place.dto.PlaceResponse;
import com.befoo.befoo.domain.place.entity.BookmarkedPlace;
import com.befoo.befoo.domain.guide.entity.Guide;
import com.befoo.befoo.domain.place.entity.Place;
import com.befoo.befoo.domain.user.dto.*;
import com.befoo.befoo.domain.user.entity.User;
import com.befoo.befoo.domain.guide.service.BookmarkedGuideService;
import com.befoo.befoo.domain.place.service.BookmarkedPlaceService;
import com.befoo.befoo.domain.guide.service.GuideService;
import com.befoo.befoo.domain.place.service.ReviewService;
import com.befoo.befoo.global.entity.enums.ContentType;
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
        myList.addAll(convertGuidesToMyListItems(guideService.findByUser(user), user));

        // 추천 식당 목록 조회
        myList.addAll(convertPlacesToMyListItems(reviewService.findRecommendedPlacesByUser(user), user));

        // updatedAt 기준으로 정렬
        sortByUpdatedAt(myList, MyListItem::getUpdatedAt);

        return MyListResponse.from(myList);
    }

    // API: 저장 목록 조회
    @Transactional(readOnly = true)
    public BookmarkedListResponse getBookmarkedList(User user) {
        List<BookmarkedListItem> bookmarkedList = new ArrayList<>();

        // 저장된 가이드 목록 조회
        bookmarkedList.addAll(convertGuidesToBookmarkedListItems(bookmarkedGuideService.findBookmarkedGuidesByUser(user), user));

        // 저장된 식당 목록 조회
        bookmarkedList.addAll(convertPlacesToBookmarkedListItems(bookmarkedPlaceService.findBookmarkedPlacesByUser(user), user));

        // updatedAt 기준으로 정렬
        sortByUpdatedAt(bookmarkedList, BookmarkedListItem::getBookmarkedAt);

        return BookmarkedListResponse.from(bookmarkedList);
    }

    // API: 닉네임 중복 확인
    public boolean isUsernameAvailable(String username) {
        return userService.isUsernameAvailable(username);
    }

    private <T> void sortByUpdatedAt(List<T> items, java.util.function.Function<T, java.time.LocalDateTime> updatedAtExtractor) {
        items.sort(Comparator.comparing(updatedAtExtractor, Comparator.nullsLast(Comparator.reverseOrder())));
    }

    private List<MyListItem> convertGuidesToMyListItems(List<Guide> guides, User user) {
        return guides.stream()
                .map(guide -> GuideResponse.from(guide)
                        .withBookmarked(bookmarkedGuideService.isBookmarked(user, guide)))
                .map(guideResponse -> MyListItem.builder()
                        .contentType(ContentType.GUIDE)
                        .guideResponse(guideResponse)
                        .updatedAt(guideResponse.getUpdatedAt())
                        .build())
                .toList();
    }

    private List<MyListItem> convertPlacesToMyListItems(List<Place> places, User user) {
        return places.stream()
                .map(place -> PlaceResponse.from(place)
                        .withBookmarked(bookmarkedPlaceService.isBookmarked(user, place)))
                .map(placeResponse -> MyListItem.builder()
                        .contentType(ContentType.PLACE)
                        .placeResponse(placeResponse)
                        .updatedAt(placeResponse.getUpdatedAt())
                        .build())
                .toList();
    }

    private List<BookmarkedListItem> convertGuidesToBookmarkedListItems(List<Guide> guides, User user) {
        return guides.stream()
                .map(guide -> {
                    BookmarkedGuide bookmarkedGuide = bookmarkedGuideService.findBookmarkedGuideByUserAndGuide(user, guide);
                    return BookmarkedListItem.builder()
                            .contentType(ContentType.GUIDE)
                            .guideResponse(GuideResponse.from(guide).withBookmarked(true))
                            .bookmarkedAt(bookmarkedGuide.getUpdatedAt())
                            .build();
                })
                .toList();
    }

    private List<BookmarkedListItem> convertPlacesToBookmarkedListItems(List<Place> places, User user) {
        return places.stream()
                .map(place -> {
                    BookmarkedPlace bookmarkedPlace = bookmarkedPlaceService.findBookmarkedPlaceByUserAndPlace(user, place);
                    return BookmarkedListItem.builder()
                        .contentType(ContentType.PLACE)
                        .placeResponse(PlaceResponse.from(place).withBookmarked(true))
                        .bookmarkedAt(bookmarkedPlace.getUpdatedAt())
                        .build();
                })
                .toList();
    }
}
