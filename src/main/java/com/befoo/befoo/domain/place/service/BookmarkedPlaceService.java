package com.befoo.befoo.domain.place.service;

import com.befoo.befoo.domain.place.entity.BookmarkedPlace;
import com.befoo.befoo.domain.place.entity.Place;
import com.befoo.befoo.domain.user.entity.User;
import com.befoo.befoo.domain.place.exception.PlaceException;
import com.befoo.befoo.domain.place.repository.BookmarkedPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkedPlaceService {
    private final BookmarkedPlaceRepository bookmarkedPlaceRepository;

    @Transactional
    public BookmarkedPlace createBookmarkedPlace(User user, Place place) {
        if (bookmarkedPlaceRepository.existsByUserAndPlace(user, place)) {
            throw PlaceException.alreadyBookmarked(place.getId());
        }
        return bookmarkedPlaceRepository.save(BookmarkedPlace.of(user, place));
    }

    @Transactional
    public void deleteBookmarkedPlace(User user, Place place) {
        BookmarkedPlace bookmarkedPlace = bookmarkedPlaceRepository.findByUserAndPlace(user, place)
                .orElseThrow(() -> PlaceException.notBookmarked(place.getId()));
        bookmarkedPlaceRepository.delete(bookmarkedPlace);
    }

    public List<Place> findBookmarkedPlacesByUser(User user) {
        return bookmarkedPlaceRepository.findByUser(user).stream()
                .map(BookmarkedPlace::getPlace)
                .toList();
    }   

    public BookmarkedPlace findBookmarkedPlaceByUserAndPlace(User user, Place place) {
        return bookmarkedPlaceRepository.findByUserAndPlace(user, place)
                .orElseThrow(() -> PlaceException.notBookmarked(place.getId()));
    }

    public boolean isBookmarked(User user, Place place) {
        if (user == null) {
            return false;
        }
        return bookmarkedPlaceRepository.existsByUserAndPlace(user, place);
    }
} 