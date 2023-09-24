package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.MockitoTestBase;
import br.com.pitang.user.car.api.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserLoggedServiceTest extends MockitoTestBase {

    UserLoggedService userLoggedService = new UserLoggedService();

    @Test
    void getUserAuthenticated() {
        var user = User.builder().id(1L).login("ze").build();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,"123"));
        assertEquals(user, userLoggedService.getUserAuthenticated());
    }
}