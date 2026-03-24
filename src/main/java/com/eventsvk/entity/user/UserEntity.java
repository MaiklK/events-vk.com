package com.eventsvk.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vk_users")
public class UserEntity {
    @Id
    private Long userVkId;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String birthdayDate;
    @Column
    private int sex;
    @Column
    private boolean isClosed;
    @Column
    private String photoBig;
    @Column
    private String photoId;
    @Column
    private boolean isLocked;
    @Column
    private Long cityId;

    @Fetch(FetchMode.JOIN)
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserCountersEntity counters;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserPersonalEntity userPersonal;

    @Override
    public String toString() {
        return """
                User
                { id : %d, Имя : %s
                Фамилия : %s, account is banned: %s}"""
                .formatted(userVkId, firstName, lastName, isLocked);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity userEntity)) return false;
        return Objects.equals(userVkId, userEntity.userVkId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userVkId);
    }
}
