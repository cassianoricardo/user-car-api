package br.com.pitang.user.car.api.service.car;

import br.com.pitang.user.car.api.MockitoTestBase;
import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.dto.CarDTO;
import br.com.pitang.user.car.api.model.entity.Car;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.service.user.UserLoggedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarFindServiceTest extends MockitoTestBase {

    @InjectMocks
    CarFindService carFindService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserLoggedService userLoggedService;


    @Test
    @DisplayName("findAll ::  should to return all cars this user")
    void should_to_return_all_cars_this_user() {

        var user = User.builder().id(1L).build();
        var car1 = Car.builder().id(1L).year(2020).color("preto").licensePlate("aaa-111").model("argo").user(user).build();
        var car2 = Car.builder().id(2L).year(2020).color("preto").licensePlate("bbb-222").model("pulse").user(user).build();

        var car1Expected = CarDTO.builder().id(1L).year(2020).color("preto").licensePlate("aaa-111").model("argo").build();
        var car12xpected = CarDTO.builder().id(2L).year(2020).color("preto").licensePlate("bbb-222").model("pulse").build();

        var carsDTOExpected = List.of(car1Expected,car12xpected);

        when(userLoggedService.getUserAuthenticated()).thenReturn(user);
        when(carRepository.findByUserId(user.getId())).thenReturn(List.of(car1, car2));

        assertEquals(carsDTOExpected, carFindService.findAll());

        verify(userLoggedService).getUserAuthenticated();
        verify(carRepository).findByUserId(user.getId());
    }

    @Test
    @DisplayName("findById ::  should to return the car this user by id ")
    void should_to_return_the_car_this_user_by_id() {

        var user = User.builder().id(2L).build();
        var car = Car.builder().id(1L).year(2020).color("preto").licensePlate("aaa-111").model("argo").user(user).build();
        var carDTOExpected = CarDTO.builder().id(1L).year(2020).color("preto").licensePlate("aaa-111").model("argo").build();

        when(userLoggedService.getUserAuthenticated()).thenReturn(user);
        when(carRepository.findByIdAndUserId(car.getId(), user.getId())).thenReturn(Optional.of(car));

        assertEquals(carDTOExpected, carFindService.findById(car.getId()));

        verify(userLoggedService).getUserAuthenticated();
        verify(carRepository).findByIdAndUserId(car.getId(), user.getId());
    }

    @Test
    @DisplayName("findById ::  should to valid car id not found")
    void should_to_valid_car_id_not_found() {

        var user = User.builder().id(2L).build();

        when(userLoggedService.getUserAuthenticated()).thenReturn(user);
        when(carRepository.findByIdAndUserId(1L, user.getId())).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class, ()-> carFindService.findById(1L));
        assertEquals("Car id: 1 not found", exception.getMessage());

        verify(userLoggedService).getUserAuthenticated();
        verify(carRepository).findByIdAndUserId(1L, user.getId());
    }
}