package com.befoo.befoo.domain.place.entity;

import com.befoo.befoo.domain.user.entity.User;
import com.befoo.befoo.global.entity.BaseTime;
import de.huxhorn.sulky.ulid.ULID;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTime {
    @Id
    @Column(name = "review_id")
    @Builder.Default
    private final String id = new ULID().nextULID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    private String receipt;

    private Integer taste;

    private Integer menu;

    private Integer nutrition;

    private Integer health;

    private Integer totalScore;

    private String comment;

    private Boolean recommend;

    public void update(Integer taste, Integer menu, Integer nutrition,
                      Integer health, Integer totalScore, String comment) {
        this.taste = taste;
        this.menu = menu;
        this.nutrition = nutrition;
        this.health = health;
        this.totalScore = totalScore;
        this.comment = comment;
    }
} 