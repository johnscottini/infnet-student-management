package com.example.auth.services;

import com.example.auth.domain.user.User;
import com.example.auth.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorizationService authorizationService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setLogin("jonathan");
        user.setPassword("123456");
    }

    @Test
    void shouldLoadUserByUsernameSuccessfully() {
        when(userRepository.findByLogin("jonathan")).thenReturn(user);

        UserDetails result = authorizationService.loadUserByUsername("jonathan");

        assertNotNull(result);
        assertEquals("jonathan", result.getUsername());
        verify(userRepository, times(1)).findByLogin("jonathan");
    }
}
