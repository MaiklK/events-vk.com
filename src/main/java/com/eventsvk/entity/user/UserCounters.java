package com.eventsvk.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_counters")
public class UserCounters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
    private int clipsFollowers;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
