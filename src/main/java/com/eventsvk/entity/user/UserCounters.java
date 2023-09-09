package com.eventsvk.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_counters")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
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
    @Column
    private int clipsFollowers;

    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
