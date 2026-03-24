package com.eventsvk.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_counters")
public class UserCountersEntity {
    @Id
    private Long userVkId;
    @Column
    private int albums;
    @Column
    private int audios;
    @Column
    private int followers;
    @Column
    private int friends;
    @Column
    private int gifts;
    @Column
    private int groups;
    @Column
    private int pages;
    @Column
    private int photos;
    @Column
    private int videos;
    @Column(name = "clips_followers")
    private Long clipsFollowers;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_vk_id", referencedColumnName = "user_vk_id")
    private UserEntity user;
}
