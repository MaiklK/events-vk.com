package com.eventsvk.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_seq_gen")
    @SequenceGenerator(name = "user_role_seq_gen", sequenceName = "user_role_seq", allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> userEntities;

    @Override
    public String getAuthority() {
        return getName();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
