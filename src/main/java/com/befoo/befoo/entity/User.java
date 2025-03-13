package com.befoo.befoo.entity;

import com.befoo.befoo.entity.enums.Allergy;
import com.befoo.befoo.entity.enums.HealthCondition;
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

    private String email;

    private String password;

    private String name;

    private String image;
    
    @ElementCollection
    @CollectionTable(name = "user_health_conditions", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<HealthCondition> healthConditions;
    
    @ElementCollection
    @CollectionTable(name = "user_allergies", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Allergy> allergies;
}