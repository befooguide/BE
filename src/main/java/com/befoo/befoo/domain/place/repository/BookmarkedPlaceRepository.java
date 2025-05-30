package com.befoo.befoo.domain.place.repository;

import com.befoo.befoo.domain.place.entity.BookmarkedPlace;
import com.befoo.befoo.domain.place.entity.Place;
import com.befoo.befoo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkedPlaceRepository extends JpaRepository<BookmarkedPlace, String> {
    List<BookmarkedPlace> findByUser(User user);
    Optional<BookmarkedPlace> findByUserAndPlace(User user, Place place);
    boolean existsByUserAndPlace(User user, Place place);
}
