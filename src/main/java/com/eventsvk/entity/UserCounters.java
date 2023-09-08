package com.eventsvk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_counters")
@Getter
@Setter
@NoArgsConstructor
public class UserCounters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
    private int subscriptions;
    @Column
    private int videos;
    @Column(name = "new_photo_tags")
    private int newPhotoTags;
    @Column(name = "new recognition_tags")
    private int newRecognitionTags;
    @Column(name = "clips_followers")
    private int clipsFollowers;

    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
