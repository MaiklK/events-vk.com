package com.eventsvk.dto.user;

import com.eventsvk.entity.user.RoleEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {
    private Long userVkId;
    private String firstName;
    private String lastName;
    private String birthdayDate;
    private String sex;
    private boolean isClosed;
    private String photoBig;
    private boolean isLocked;
    private Set<RoleEntity> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(userVkId, userDto.userVkId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userVkId);
    }
}
