package com.befoo.befoo.domain.repository;

import com.befoo.befoo.domain.entity.Guide;
import com.befoo.befoo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideRepository extends JpaRepository<Guide, String> {
    List<Guide> findByUser(User user);
}
