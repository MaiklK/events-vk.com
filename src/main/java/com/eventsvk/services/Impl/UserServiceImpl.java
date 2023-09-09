package com.eventsvk.services.Impl;

import com.eventsvk.entity.user.User;
import com.eventsvk.repositories.UserRepository;
import com.eventsvk.security.CastomUserDetails;
import com.eventsvk.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findUserByVkid(String vkid) {
        return userRepository.findByVkid(vkid).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(User updateUser) {
        updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        return userRepository.save(updateUser);
    }

    @Override
    public UserDetails loadUserByUsername(String userVkid) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByVkid(userVkid);

        if (foundUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CastomUserDetails(foundUser.get());
    }
}
