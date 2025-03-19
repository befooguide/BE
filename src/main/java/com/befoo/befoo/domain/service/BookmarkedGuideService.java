package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.entity.BookmarkedGuide;
import com.befoo.befoo.domain.entity.Guide;
import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.exception.GuideException;
import com.befoo.befoo.domain.repository.BookmarkedGuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkedGuideService {
    private final BookmarkedGuideRepository bookmarkedGuideRepository;

    public void createBookmarkedGuide(User user, Guide guide) {
        if (bookmarkedGuideRepository.existsByUserAndGuide(user, guide)) {
            throw GuideException.alreadyBookmarked(guide.getId());
        }
        BookmarkedGuide bookmarkedGuide = BookmarkedGuide.builder()
                .user(user)
                .guide(guide)
                .build();
        bookmarkedGuideRepository.save(bookmarkedGuide);
    }

    public void deleteBookmarkedGuide(User user, Guide guide) {
        BookmarkedGuide bookmarkedGuide = bookmarkedGuideRepository.findByUserAndGuide(user, guide)
                .orElseThrow(() -> GuideException.notBookmarked(guide.getId()));
        bookmarkedGuideRepository.delete(bookmarkedGuide);
    }

    public List<Guide> findBookmarkedGuidesByUser(User user) {
        return bookmarkedGuideRepository.findByUser(user).stream()
                .map(BookmarkedGuide::getGuide)
                .collect(Collectors.toList());
    }

    public boolean isBookmarked(User user, Guide guide) {
        if (user == null) {
            return false;
        }
        return bookmarkedGuideRepository.existsByUserAndGuide(user, guide);
    }
} 