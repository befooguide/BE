package com.befoo.befoo.domain.place.entity;

import com.befoo.befoo.global.entity.BaseTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    private String description;

    private String url;

    private String image;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<Review> reviews;
} 