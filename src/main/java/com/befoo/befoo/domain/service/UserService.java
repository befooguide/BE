package com.befoo.befoo.domain.service;

import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.entity.enums.Allergy;
import com.befoo.befoo.domain.entity.enums.HealthCondition;
import com.befoo.befoo.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUser(User user) {
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public User updateHealthInfo(User user, List<HealthCondition> healthConditions, List<Allergy> allergies) {
        User updatedUser = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .name(user.getName())
                .image(user.getImage())
                .healthConditions(healthConditions)
                .allergies(allergies)
                .build();
        return userRepository.save(updatedUser);
    }
}
