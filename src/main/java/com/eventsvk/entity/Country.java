package com.eventsvk.entity;

import com.eventsvk.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
public class Country {
    @Id
    private int id;
    @Column
    private String title;

    @JsonBackReference
    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<User> user;

    public Country(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
