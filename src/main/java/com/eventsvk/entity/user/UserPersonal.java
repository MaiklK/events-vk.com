package com.eventsvk.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "user_personal")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPersonal {
    @Id
    private String userId;
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
    @JoinColumn(name = "user_id", referencedColumnName = "vk_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPersonal that)) return false;
        return political == that.political && peopleMain == that.peopleMain && lifeMain == that.lifeMain
                && smoking == that.smoking && alcohol == that.alcohol && Objects.equals(inspiredBy, that.inspiredBy)
                && Objects.equals(langs, that.langs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(political, inspiredBy, peopleMain, lifeMain, smoking, alcohol, langs);
    }
}
