package com.befoo.befoo.domain.repository;

import com.befoo.befoo.domain.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository extends JpaRepository<Guide, String> {
}
