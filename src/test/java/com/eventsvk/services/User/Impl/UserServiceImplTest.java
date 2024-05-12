package com.eventsvk.services.User.Impl;

import com.eventsvk.entity.City;
import com.eventsvk.entity.Country;
import com.eventsvk.entity.user.User;
import com.eventsvk.entity.user.UserCounters;
import com.eventsvk.entity.user.UserPersonal;
import com.eventsvk.repositories.UserRepository;
import com.eventsvk.services.CityService;
import com.eventsvk.services.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private CityService cityService;
    @Mock
    private CountryService countryService;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setVkid("12345");
        user.setPassword("password");
        user.setCity(new City());
        user.setCountry(new Country());
        user.setUserPersonal(new UserPersonal());
        user.setCounters(new UserCounters());
    }

    @Test
    void testSaveUserWhenUserIsValidThenUserIsSaved() {
        when(cityService.findCityById(anyInt())).thenReturn(new City());
        when(countryService.findCountryById(anyInt())).thenReturn(new Country());
        when(userRepository.findByVkid(anyString())).thenReturn(Optional.of(user));
        userService.saveUser(user);
        verify(userRepository).save(user);
    }

    @Test
    void testFindUserByVkidWhenUserExistsThenUserIsReturned() {
        when(userRepository.findByVkid(anyString())).thenReturn(Optional.of(user));
        User foundUser = userService.findUserByVkid("12345");
        assertEquals(user, foundUser);
    }

    @Test
    void testGetAllUsersWhenUsersExistThenAllUsersAreReturned() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> users = userService.getAllUsers();
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }

    @Test
    void testFindUserByIdWhenUserExistsThenUserIsReturned() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        User foundUser = userService.findUserById(1L);
        assertEquals(user, foundUser);
    }

    @Test
    void testBanUserWhenUserExistsThenUserIsBanned() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        userService.banUser(1L);
        verify(userRepository).save(user);
        assertFalse(user.isAccountNonLocked());
    }

    @Test
    void testUpdateUserWhenUserIsValidThenUserIsUpdated() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        User updatedUser = userService.updateUser(user);
        verify(userRepository).save(user);
        assertEquals("encodedPassword", updatedUser.getPassword());
    }

    @Test
    void testLoadUserByUsernameWhenUserExistsThenUserDetailsIsReturned() {
        when(userRepository.findByVkid(anyString())).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername("12345");
        assertNotNull(userDetails);
        assertEquals(user.getVkid(), userDetails.getUsername());
    }

    @Test
    void testFindUserByVkidWhenUserDoesNotExistThenExceptionIsThrown() {
        when(userRepository.findByVkid(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.findUserByVkid("12345"));
    }
}