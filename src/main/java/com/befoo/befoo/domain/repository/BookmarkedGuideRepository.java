package com.befoo.befoo.domain.repository;

import com.befoo.befoo.domain.entity.BookmarkedGuide;
import com.befoo.befoo.domain.entity.Guide;
import com.befoo.befoo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkedGuideRepository extends JpaRepository<BookmarkedGuide, String> {
    List<BookmarkedGuide> findByUser(User user);
    Optional<BookmarkedGuide> findByUserAndGuide(User user, Guide guide);
    boolean existsByUserAndGuide(User user, Guide guide);
}
