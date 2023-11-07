package com.eventsvk.services.User.Impl;

import com.eventsvk.entity.City;
import com.eventsvk.entity.Country;
import com.eventsvk.entity.user.User;
import com.eventsvk.entity.user.UserCounters;
import com.eventsvk.entity.user.UserPersonal;
import com.eventsvk.repositories.UserRepository;
import com.eventsvk.security.CustomUserDetails;
import com.eventsvk.services.CityService;
import com.eventsvk.services.CountryService;
import com.eventsvk.services.User.UserService;
import lombok.RequiredArgsConstructor;
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
    private final CityService cityRepository;
    private final CountryService countryService;

    @Override
    @Transactional
    public void saveUser(User user) {
        if (user.getCity() != null) {
            City city = cityRepository.findCityById(user.getCity().getId());
            if (city != null) {
                user.setCity(city);
            }
        }
        if (user.getCountry() != null) {
            Country country = countryService.findCountryById(user.getCountry().getId());
            if (country != null) {
                user.setCountry(country);
            }
        }
        if (findUserByVkid(user.getVkid()) != null) {
            UserPersonal userPersonal = user.getUserPersonal();
            UserCounters userCounters = user.getCounters();
            userPersonal.setId(user.getId());
            userCounters.setId(user.getId());
            user.setUserPersonal(userPersonal);
            user.setCounters(userCounters);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user.getUserPersonal().setUser(user);
        user.getCounters().setUser(user);
        userRepository.save(user);
    }

    @Override
    public User findUserByVkid(String vkid) {
        Optional<User> foundUser = userRepository.findByVkid(vkid);
        return foundUser.orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с vkid: " + vkid + " не найден"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(long userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        return foundUser.orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с id: " + userId + " не найден"));
    }

    @Transactional
    @Override
    public void banUser(long userId) {
        User user = findUserById(userId);
        user.setAccountNonLocked(false);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(User updateUser) {
        updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        return userRepository.save(updateUser);
    }

    @Override
    public UserDetails loadUserByUsername(String userVkid) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByVkid(userVkid);

        return new CustomUserDetails(foundUser.orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с vkid: " + userVkid + " не найден")));
    }
}
