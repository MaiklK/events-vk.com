package com.eventsvk.services;

import com.eventsvk.models.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    User findUserById(long id);

    List<User> findAllUsers();

    void updateUser(User user, long id);

    void deleteUser(long id);

    public User findByUsername(String name) throws Exception;
}
