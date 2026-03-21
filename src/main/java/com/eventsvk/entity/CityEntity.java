package com.eventsvk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private long id;
    @Column
    private String title;
    @Column
    private String area;
    @Column
    private String region;
}
