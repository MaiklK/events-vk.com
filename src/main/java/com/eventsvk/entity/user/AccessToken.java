package com.eventsvk.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "access_tokens")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AccessToken {
    @Id
    private String id;
    @Column(name = "is_in_use")
    private boolean isInUse;
    @Column(name = "is_valid")
    private boolean isValid;
    @Column
    private String token;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessToken that = (AccessToken) o;
        return Objects.equals(id, that.id) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token);
    }
}
