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
    private Integer political;
    @Column
    private String inspiredBy;
    @Column
    private Integer peopleMain;
    @Column
    private Integer lifeMain;
    @Column
    private Integer smoking;
    @Column
    private Integer alcohol;

    @JsonBackReference
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_vk_id")
    private UserEntity user;
}
