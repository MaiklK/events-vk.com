package com.eventsvk.services.User.Impl;

import com.eventsvk.entity.City;
import com.eventsvk.entity.Country;
import com.eventsvk.entity.user.User;
import com.eventsvk.entity.user.UserCounters;
import com.eventsvk.entity.user.UserPersonal;
import com.eventsvk.repositories.UserRepository;
import com.eventsvk.security.CastomUserDetails;
import com.eventsvk.services.CityService;
import com.eventsvk.services.CountryService;
import com.eventsvk.services.User.UserCountersService;
import com.eventsvk.services.User.UserPersonalService;
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
    private final UserCountersService userCountersService;
    private final UserPersonalService userPersonalService;

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

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

        userRepository.save(user);

        UserCounters counters = user.getCounters();
        if (counters != null) {
            counters.setUser(user);
            userCountersService.saveUserCounters(counters);
        }

        UserPersonal userPersonal = user.getUserPersonal();
        if (userPersonal != null) {
            userPersonal.setUser(user);
            userPersonalService.saveUserPersonal(userPersonal);
        }
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
        System.out.println(updateUser);
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
