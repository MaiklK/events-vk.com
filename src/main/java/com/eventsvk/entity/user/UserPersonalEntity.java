package com.eventsvk.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "user_personal")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPersonalEntity {
    @Id
    private Long userVkId;
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

    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_vk_id", referencedColumnName = "user_vk_id")
    private UserEntity user;
}
