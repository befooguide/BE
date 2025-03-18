package com.befoo.befoo.domain.repository;

import com.befoo.befoo.domain.entity.BookmarkedPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkedPlaceRepository extends JpaRepository<BookmarkedPlace, String> {
}
