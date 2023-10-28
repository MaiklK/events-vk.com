package com.eventsvk.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "access_tokens")
@Getter
@Setter
@NoArgsConstructor
public class AccessToken {
    @Id
    private long id;
    @Column(name = "is_in_use")
    private boolean isInUse;
    @Column(name = "is_valid")
    private boolean isValid;
    @Column
    private String token;
}
