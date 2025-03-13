package com.befoo.befoo.domain.entity;

import com.befoo.befoo.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

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

    private String name;

    private String description;
} 