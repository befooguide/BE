package com.befoo.befoo.domain.user.dto;

import com.befoo.befoo.global.dto.Response;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookmarkedListResponse implements Response {
    private List<BookmarkedListItem> bookmarkedList;

    public static BookmarkedListResponse from(List<BookmarkedListItem> bookmarkedList) {
        return BookmarkedListResponse.builder()
                .bookmarkedList(bookmarkedList)
                .build();
    }
} 