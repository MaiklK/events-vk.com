package com.eventsvk.entity.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "allowed_city")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowedCityEntity {
    @Id
    private Long cityId;
    @Column
    private Long countryId;
    @Column
    private String cityName;
}
