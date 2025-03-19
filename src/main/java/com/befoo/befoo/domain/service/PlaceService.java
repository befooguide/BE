package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.entity.Place;
import com.befoo.befoo.domain.exception.PlaceException;
import com.befoo.befoo.domain.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {
    private final PlaceRepository placeRepository;

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public Place getPlaceById(String placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> PlaceException.notFound(placeId));
    }

    public Place findById(String placeId) {
        return getPlaceById(placeId);
    }

    public boolean isBookmarked(String placeId, String userId) {
        // TODO: 북마크 여부 확인 로직 구현
        return false;
    }
}
