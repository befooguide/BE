package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.dto.GuideRequest;
import com.befoo.befoo.domain.entity.Guide;
import com.befoo.befoo.domain.entity.GuidePlace;
import com.befoo.befoo.domain.entity.Place;
import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.exception.GuideException;
import com.befoo.befoo.domain.repository.GuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuideService {
    private final GuideRepository guideRepository;

    @Transactional
    public Guide createGuide(User user, GuideRequest request, List<Place> places) {
        Guide guide = Guide.builder()
                .user(user)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        List<GuidePlace> guidePlaces = places.stream()
                .map(place -> GuidePlace.builder()
                        .guide(guide)
                        .place(place)
                        .build())
                .collect(Collectors.toList());

        guide.getGuidePlaces().addAll(guidePlaces);
        return guideRepository.save(guide);
    }

    @Transactional
    public Guide updateGuide(String guideId, GuideRequest request, List<Place> places) {
        Guide guide = findById(guideId);
        guide.update(request.getName(), request.getDescription());
        
        guide.getGuidePlaces().clear();
        List<GuidePlace> guidePlaces = places.stream()
                .map(place -> GuidePlace.builder()
                        .guide(guide)
                        .place(place)
                        .build())
                .collect(Collectors.toList());
        guide.getGuidePlaces().addAll(guidePlaces);
        
        return guideRepository.save(guide);
    }

    @Transactional
    public void deleteGuide(String guideId) {
        Guide guide = findById(guideId);
        guideRepository.delete(guide);
    }

    public Guide findById(String guideId) {
        return guideRepository.findById(guideId)
                .orElseThrow(() -> GuideException.notFound(guideId));
    }

    public List<Guide> findByUser(User user) {
        return guideRepository.findByUser(user);
    }

    public void validateGuideBelongsToUser(Guide guide, String userId) {
        if (!guide.getUser().getId().equals(userId)) {
            throw GuideException.forbidden(guide.getId());
        }
    }

    public List<Guide> findAll() {
        return guideRepository.findAll();
    }
}
