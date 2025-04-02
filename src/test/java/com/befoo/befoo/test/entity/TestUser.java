package com.befoo.befoo.test.entity;

import com.befoo.befoo.domain.entity.User;
import com.befoo.befoo.domain.entity.enums.Allergy;
import com.befoo.befoo.domain.entity.enums.HealthCondition;
import com.befoo.befoo.domain.entity.enums.Role;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 테스트에서 사용할 User 엔티티
 * 기본적인 테스트 데이터를 포함하고 있습니다.
 */
public class TestUser {
    
    /**
     * 기본 테스트 사용자 생성
     * @return User 엔티티
     */
    public static User createDefaultUser() {
        return User.builder()
                .id("test-user-id")
                .username("테스트사용자")
                .email("test@example.com")
                .role(Role.ROLE_USER)
                .healthConditions(new ArrayList<>(Arrays.asList(
                    HealthCondition.DIABETES,
                    HealthCondition.HYPERTENSION
                )))
                .allergies(new ArrayList<>(Arrays.asList(
                    Allergy.EGG,
                    Allergy.WHEAT
                )))
                .build();
    }
} 