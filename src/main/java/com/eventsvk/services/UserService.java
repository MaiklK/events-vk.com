package com.eventsvk.services;

import com.eventsvk.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void saveUser(User user);

    User findUserByVkid(String userVKid);

    List<User> getAllUsers();

    User findUserByUuid(String userUuid);

    void deleteUser(String userUuid);

    User updateUser(User updateUser);
}
