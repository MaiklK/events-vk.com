package com.eventsvk.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_personal")
public class UserPersonal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private int political;
    @Column(name = "inspire_by")
    private String inspiredBy;
    @Column(name = "people_main")
    private int peopleMain;
    @Column(name = "life_main")
    private int lifeMain;
    @Column
    private int smoking;
    @Column
    private int alcohol;
    @ElementCollection
    @CollectionTable(name = "user_langs", joinColumns = @JoinColumn(name = "user_personal_id"))
    @Column(name = "lang")
    private List<String> langs;

    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
