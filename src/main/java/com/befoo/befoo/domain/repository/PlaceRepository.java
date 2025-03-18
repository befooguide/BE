package com.befoo.befoo.domain.repository;

import com.befoo.befoo.domain.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, String> {
}
