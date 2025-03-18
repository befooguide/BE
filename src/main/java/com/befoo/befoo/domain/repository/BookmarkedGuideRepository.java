package com.befoo.befoo.domain.repository;

import com.befoo.befoo.domain.entity.BookmarkedGuide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkedGuideRepository extends JpaRepository<BookmarkedGuide, String> {
}
