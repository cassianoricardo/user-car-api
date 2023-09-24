package br.com.pitang.user.car.api.service.impl;

import br.com.pitang.user.car.api.MockitoTestBase;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest extends MockitoTestBase {


    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    UserRepository userRepository;


    @Test
    @DisplayName("loadUserByUsername :: should to return user by login")
    void should_to_return_user_by_login() {

        var user = User.builder().id(1L).login("ze").build();
        var userExpected = User.builder().id(1L).login("ze").build();

        when(userRepository.findByLogin(user.getUsername())).thenReturn(Optional.of(user));
        assertEquals(userExpected, userDetailsService.loadUserByUsername(user.getUsername()));
        verify(userRepository).findByLogin(user.getUsername());
    }

    @Test
    @DisplayName("loadUserByUsername :: should to return user not found")
    void should_to_return_user_not_found() {

        when(userRepository.findByLogin("ze")).thenReturn(Optional.empty());
        var exception = assertThrows(UsernameNotFoundException.class, ()-> userDetailsService.loadUserByUsername("ze"));
        assertEquals("User not found with login: ze", exception.getMessage());
        verify(userRepository).findByLogin("ze");
    }
}