package com.eventsvk.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cities")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityEntity {
    @Id
    private Long id;
    @Column(unique = true)
    private String title;
    @Column(unique = true)
    private String area;
    @Column(unique = true)
    private String region;
}
