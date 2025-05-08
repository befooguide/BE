package com.befoo.befoo.domain.guide.repository;

import com.befoo.befoo.domain.guide.entity.GuidePlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuidePlaceRepository extends JpaRepository<GuidePlace, String> {
}
