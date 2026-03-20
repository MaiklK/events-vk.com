package com.eventsvk.entity.user;

import com.eventsvk.entity.City;
import com.eventsvk.entity.Country;
import com.eventsvk.enums.Sex;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private Long vkId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "mobile_phone")
    private String mobilePhone;
    @Column
    private String password;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "birthday_date")
    private String birthdayDate;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private Sex Sex;
    @Column(name = "is_closed")
    private boolean isClosed;
    @Column(name = "photo_big")
    private String photoBig;
    @Column(name = "photo_id")
    private String photoId;
    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked;

    @Fetch(FetchMode.JOIN)
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_city",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id"))
    private City city;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_country",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private Country country;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserCounters counters;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserPersonal userPersonal;

    @Override
    public String toString() {
        return "User\n{ id : "
                + this.vkId + ", Имя : "
                + this.firstName + "\nФамилия : "
                + this.lastName + "account is banned: "
                + this.isAccountNonLocked + "Роли: "
                + this.roles + " }";
    }

    public String getRolesAsString() {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return vkId.equals(user.vkId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vkId);
    }
}
