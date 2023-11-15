package com.eventsvk.entity.user;

import com.eventsvk.entity.City;
import com.eventsvk.entity.Country;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User implements UserDetails {
    @Id
    private String vkid;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "mobile_phone")
    private String mobilePhone;
    @Column
    private String password;
    @Column(name = "access token")
    private String accessToken;
    @Column(name = "birthday_date")
    private String birthdayDate;
    @Column
    private int sex;
    @Column(name = "is_closed")
    private boolean isClosed;
    @Column(name = "photo_big")
    private String photoBig;
    @Column(name = "photo_id")
    private String photoId;
    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked;

    @Fetch(FetchMode.JOIN)
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "user_city",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id"))
    private City city;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "user_country",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private Country country;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserCounters counters;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserPersonal userPersonal;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.vkid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User\n{ id : "
                + this.vkid + ", Имя : "
                + this.firstName + "\nФамилия : "
                + this.lastName + "account is banned: "
                + this.isAccountNonLocked + "Город: "
                + this.city.getTitle() + "Страна: "
                + this.country.getTitle() + "Роли: "
                + this.roles + " }";
    }

    public String rolesToString() {
        StringBuilder str = new StringBuilder();

        this.roles.forEach(role -> str.append(role.getName()));

        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return sex == user.sex && isClosed == user.isClosed && Objects.equals(vkid, user.vkid)
                && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName)
                && Objects.equals(mobilePhone, user.mobilePhone) && Objects.equals(accessToken, user.accessToken)
                && Objects.equals(birthdayDate, user.birthdayDate) && Objects.equals(photoBig, user.photoBig)
                && Objects.equals(photoId, user.photoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vkid, firstName, lastName, mobilePhone, accessToken, birthdayDate,
                sex, isClosed, photoBig, photoId);
    }
}
