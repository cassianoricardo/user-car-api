package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.MockitoTestBase;
import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.dto.CarDTO;
import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.model.entity.Car;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserFindServiceTest extends MockitoTestBase {

    @InjectMocks
    UserFindService userFindService;

    @Mock
    UserRepository userRepository;

    @Mock
    CarRepository carRepository;

    @Test
    @DisplayName("findById :: should to return user by id")
    void should_to_return_user_by_id() {

        var user = User.builder().id(1L).build();
        var userExpected = UserDTO.builder().id(1L).cars(emptyList()).build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carRepository.findByUserIdOrderByCountUsedDescModelAsc(1L)).thenReturn(emptyList());
        assertEquals(userExpected, userFindService.findById(1L));
        verify(userRepository).findById(1L);
        verify(carRepository).findByUserIdOrderByCountUsedDescModelAsc(1L);
    }

    @Test
    @DisplayName("findById :: should to return user not found")
    void should_to_return_user_not_found() {

        var userExpected = UserDTO.builder().id(1L).cars(emptyList()).build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> assertEquals(userExpected, userFindService.findById(1L)));
        assertEquals("User Not Found", exception.getMessage());
        verify(userRepository).findById(1L);
        verify(carRepository, never()).findByUserIdOrderByCountUsedDescModelAsc(1L);
    }

    @Test
    @DisplayName("findAll :: should to return all users")
    void should_to_return_all_users() {

        var user1 = User.builder().id(1L).fistName("user").lastName("a").build();
        var user2 = User.builder().id(2L).fistName("user").lastName("b").build();
        var user3 = User.builder().id(3L).fistName("user").lastName("c").build();
        var user4 = User.builder().id(4L).fistName("user").lastName("d").build();

        var car1 = Car.builder().id(1L).model("a").user(user1).countUsed(1).build();
        var car2 = Car.builder().id(2L).model("b").user(user1).countUsed(2).build();
        var car3 = Car.builder().id(3L).model("c").user(user2).countUsed(3).build();
        var car4 = Car.builder().id(4L).model("d").user(user2).countUsed(1).build();

        var usersDTOExpected = List.of(
                UserDTO.builder().id(2L).fistName("user").lastName("b").cars(List.of(CarDTO.builder().id(3L).model("c").build(), CarDTO.builder().id(4L).model("d").build())).build(),
                UserDTO.builder().id(1L).fistName("user").lastName("a").cars(List.of(CarDTO.builder().id(2L).model("b").build(), CarDTO.builder().id(1L).model("a").build())).build(),
                UserDTO.builder().id(3L).fistName("user").lastName("c").cars(List.of()).build(),
                UserDTO.builder().id(4L).fistName("user").lastName("d").cars(List.of()).build());

        when(userRepository.findAll()).thenReturn(List.of(user1, user2, user3, user4));
        when(carRepository.findAll()).thenReturn(List.of(car1, car2, car3, car4));

        assertEquals(usersDTOExpected, userFindService.findAll());

        verify(userRepository).findAll();
        verify(carRepository).findAll();
    }
}