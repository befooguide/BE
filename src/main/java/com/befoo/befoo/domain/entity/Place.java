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
public class Place extends BaseTime {
    @Id
    @Column(name = "place_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @Column(name = "kakaomap_id")
    private String kakaomapId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String url;


    private String image;
} 