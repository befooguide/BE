package com.befoo.befoo.domain.place.repository;

import com.befoo.befoo.domain.place.entity.Review;
import com.befoo.befoo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findByPlaceId(String placeId);
    List<Review> findByUserAndRecommendTrue(User user);
}
