package com.befoo.befoo.domain.entity;

import com.befoo.befoo.domain.entity.enums.Allergy;
import com.befoo.befoo.domain.entity.enums.HealthCondition;
import com.befoo.befoo.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guide extends BaseTime {
    @Id
    @Column(name = "guide_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

//    @ElementCollection
//    @CollectionTable(name = "user_health_conditions", joinColumns = @JoinColumn(name = "user_id"))
//    @Enumerated(EnumType.STRING)
//    private List<HealthCondition> healthConditions;
//
//    @ElementCollection
//    @CollectionTable(name = "user_allergies", joinColumns = @JoinColumn(name = "user_id"))
//    @Enumerated(EnumType.STRING)
//    private List<Allergy> allergies;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<GuidePlace> guidePlaces = new ArrayList<>();

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }
} 