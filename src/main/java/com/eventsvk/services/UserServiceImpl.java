package com.eventsvk.services;

import com.eventsvk.models.User;
import com.eventsvk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserById(long id) {
        Optional<User> foundUser = userRepository.findById(id);

        return foundUser.orElse(null);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void updateUser(User user, long id) {
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userRepository.delete(findUserById(id));
    }

    @Override
    public User findByUsername(String username) throws Exception {
        Optional<User> foundUser = userRepository.findByUsername(username);

        if (foundUser.isEmpty()){
            throw new Exception("Пользователь отсутствует в базе данных");
        }
        return foundUser.get();
    }
}
