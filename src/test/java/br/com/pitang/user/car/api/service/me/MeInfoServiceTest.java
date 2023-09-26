package br.com.pitang.user.car.api.service.me;

import br.com.pitang.user.car.api.MockitoTestBase;
import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.model.response.MeResponse;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.repository.UserRepository;
import br.com.pitang.user.car.api.service.user.UserLoggedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MeInfoServiceTest extends MockitoTestBase {

    @InjectMocks
    MeInfoService meInfoService;

    @Mock
    UserRepository userRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    UserLoggedService userLoggedService;

    @Test
    @DisplayName("getInfo :: should to return all information of logged user")
    void should_to_return_all_information_of_logged_user() {

        var userLogged = User.builder().id(1L).login("ze").build();
        var userLoggedExpected = MeResponse.builder().user(UserDTO.builder().id(1L).login("ze")
                .cars(List.of()).build()).build();

        when(userLoggedService.getUserAuthenticated()).thenReturn(userLogged);
        when(userRepository.findByLogin(userLogged.getUsername())).thenReturn(Optional.of(userLogged));
        when(carRepository.findByUserIdOrderByCountUsedDescModelAsc(userLogged.getId())).thenReturn(List.of());

        assertEquals(userLoggedExpected, meInfoService.getInfo());

        verify(userLoggedService).getUserAuthenticated();
        verify(userRepository).findByLogin(userLogged.getUsername());
        verify(carRepository).findByUserIdOrderByCountUsedDescModelAsc(userLogged.getId());
    }
}