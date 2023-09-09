package com.eventsvk.services;

import com.eventsvk.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void saveUser(User user);

    User findUserByVkid(String userVkId);

    List<User> getAllUsers();

    User findUserById(long userId);

    void banUser(long userId);

    User updateUser(User updateUser);
}
