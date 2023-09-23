package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.model.request.user.UserCreateRequest;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.Calendar;

class UserCreateServiceTest {

    @InjectMocks
    private UserCreateService userCreateService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("create :: should create the user")
    void should_create_the_user() {
        var userCreateRequest = UserCreateRequest.builder().fistName("fistName").lastName("lastName")
                                                                          .login("login").password("password")
                                                                          .birtday(new Date(Calendar.getInstance().getTime().getTime())).build();

        var userCreateRequestExpected = new UserCreateRequest();
        BeanUtils.copyProperties(userCreateRequest, userCreateRequestExpected);
            userCreateService.create(userCreateRequest);
    }
}