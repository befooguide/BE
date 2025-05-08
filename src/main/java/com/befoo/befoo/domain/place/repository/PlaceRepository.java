package com.befoo.befoo.domain.place.repository;

import com.befoo.befoo.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, String> {
}
