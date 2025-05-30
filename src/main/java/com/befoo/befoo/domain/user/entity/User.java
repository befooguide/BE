package com.befoo.befoo.domain.user.entity;

import com.befoo.befoo.domain.user.dto.UserProfileRequest;
import com.befoo.befoo.domain.user.entity.enums.Allergy;
import com.befoo.befoo.domain.user.entity.enums.HealthCondition;
import com.befoo.befoo.domain.user.entity.enums.Role;
import com.befoo.befoo.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {
    @Id
    @Column(name = "user_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @Column(nullable = false, unique = true, updatable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String nickname;

    private String image;
    
    @ElementCollection
    @CollectionTable(name = "user_health_conditions", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<HealthCondition> healthConditions;
    
    @ElementCollection
    @CollectionTable(name = "user_allergies", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Allergy> allergies;

    public void updateProfile(UserProfileRequest request) {
        this.nickname = request.getNickname();
        this.healthConditions = request.getHealthConditions();
        this.allergies = request.getAllergies();
    }
}