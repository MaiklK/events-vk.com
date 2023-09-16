package com.eventsvk.entity;

import com.eventsvk.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cities")
@Getter
@Setter
@NoArgsConstructor
public class City {
    @Id
    private int id;
    @Column
    private String title;
    @Column
    private String area;
    @Column
    private String region;

    @JsonBackReference
    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private List<User> users;
}
