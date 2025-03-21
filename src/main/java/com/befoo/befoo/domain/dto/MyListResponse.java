package com.befoo.befoo.domain.dto;

import com.befoo.befoo.global.dto.Response;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MyListResponse implements Response {
    private List<MyListItem> myList;

    public static MyListResponse from(List<MyListItem> myList) {
        return MyListResponse.builder()
                .myList(myList)
                .build();
    }
} 