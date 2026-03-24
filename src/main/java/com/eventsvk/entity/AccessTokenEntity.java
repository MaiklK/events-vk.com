package com.eventsvk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "access_tokens")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AccessTokenEntity {
    @Id
    private Long id;
    @Column(name = "is_in_use")
    private boolean isInUse;
    @Column(name = "is_valid")
    private boolean isValid;
    @Column
    private String token;
}
