package com.eventsvk.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_counters")
public class UserCounters {
    @Id
    private String user_id;
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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCounters that = (UserCounters) o;
        return albums == that.albums && audios == that.audios && followers == that.followers && friends ==
                that.friends && gifts == that.gifts && groups == that.groups && pages == that.pages && photos ==
                that.photos && videos == that.videos && clipsFollowers == that.clipsFollowers;
    }

    @Override
    public int hashCode() {
        return Objects.hash(albums, audios, followers, friends, gifts, groups, pages, photos, videos, clipsFollowers);
    }
}
