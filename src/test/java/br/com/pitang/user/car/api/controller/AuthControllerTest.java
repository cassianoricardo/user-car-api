package br.com.pitang.user.car.api.controller;

import br.com.pitang.user.car.api.MockMvcBase;
import br.com.pitang.user.car.api.model.entity.Role;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.model.request.login.LoginRequest;
import br.com.pitang.user.car.api.util.JwtUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends MockMvcBase {

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtUtils jwtUtils;

    //region authenticateUser(LoginRequest)
    @Test
    @DisplayName("POST /auth/signin - should to authenticate the user")
    void should_to_authenticate_user() throws Exception {

        var authentication = Mockito.mock(UsernamePasswordAuthenticationToken.class);

        var user = new User();
        user.setUsername("ze");
        user.setRoles(List.of(Role.builder().name("ROLE_USER").build()));

        when(authentication.getPrincipal()).thenReturn(user);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("Bearer token");

        var loginRequest = LoginRequest.builder().login("ze").password("123").build();
        performPost("/auth/signin", loginRequest)
                .andExpect(status().isOk());

        verify(authentication).getPrincipal();
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateJwtToken(authentication);
    }

    @Test
    @DisplayName("POST /auth/signin - should to valid Invalid login or password")
    void should_to_valid_invalid_login_or_password() throws Exception {

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException("Credenciais inválidas"));

        var loginRequest = LoginRequest.builder().login("ze").password("123").build();

        performPost("/auth/signin", loginRequest)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Invalid login or password"));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(jwtUtils);
    }
    //endregion
}