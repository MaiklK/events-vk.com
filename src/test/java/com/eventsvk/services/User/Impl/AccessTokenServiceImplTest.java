package com.eventsvk.services.User.Impl;

import com.eventsvk.entity.user.AccessToken;
import com.eventsvk.repositories.AccessTokenRepository;
import com.eventsvk.util.exceptions.AccessTokenNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccessTokenServiceImplTest {

    @Mock
    private AccessTokenRepository tokenRepository;
    @InjectMocks
    private AccessTokenServiceImpl sut;

    private AccessToken token1;
    private AccessToken token2;

    @BeforeEach
    public void setUp() {
        token1 = AccessToken.builder()
                .token("token1")
                .id("1")
                .isInUse(false)
                .isValid(true)
                .build();

        token2 = AccessToken.builder()
                .token("token2")
                .id("2")
                .isInUse(true)
                .isValid(false)
                .build();
    }

    @Test
    void testGetAllTokensWhenRepositoryReturnsTokensThenReturnTokens() {
        var expectedTokens = Arrays.asList(token1, token2);
        when(tokenRepository.findAll()).thenReturn(expectedTokens);

        var actualTokens = sut.getAllTokens();

        assertEquals(expectedTokens, actualTokens);
    }

    @Test
    void testGetAllTokensWhenRepositoryReturnsEmptyListThenReturnEmptyList() {
        when(tokenRepository.findAll()).thenReturn(Collections.emptyList());

        var actualTokens = sut.getAllTokens();

        assertEquals(Collections.emptyList(), actualTokens);
    }

    @SneakyThrows
    @Test
    void testGetTokenByIdSuccess() {
        var tokenId = "1";
        when(tokenRepository.findById(tokenId)).thenReturn(Optional.of(token1));

        var actualToken = sut.getTokenById(tokenId);
        var expectedToken = AccessToken.builder().id(tokenId).build();

        assertEquals(expectedToken.getId(), actualToken.getId());
    }

    @Test
    void testGetTokenNoyInUseSuccess() throws AccessTokenNotFoundException {
        when(tokenRepository.getRandomTokenNotInUse()).thenReturn(Optional.of(token1));

        var actualToken = sut.getTokenNotInUse();
        var expectedToken = AccessToken.builder()
                .isInUse(false)
                .isValid(true)
                .build();

        assertEquals(expectedToken.isInUse(), actualToken.isInUse());
        assertEquals(expectedToken.isValid(), actualToken.isValid());
        assertNotEquals(token2.isInUse(), actualToken.isInUse());
        assertNotEquals(token2.isValid(), actualToken.isValid());
    }

    @Test()
    void testGetTokenByIdThrow() {
        assertThrows(AccessTokenNotFoundException.class,
                () -> when(sut.getTokenById("666.666.666")).thenReturn(null));
    }

    @Test
    void testGetTokenNotInUseThrow() {
        assertThrows(AccessTokenNotFoundException.class,
                () -> when(sut.getTokenNotInUse()).thenReturn(null));
    }
}