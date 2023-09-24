package br.com.pitang.user.car.api.service.car;

import br.com.pitang.user.car.api.MockitoTestBase;
import br.com.pitang.user.car.api.exception.BadRequestException;
import br.com.pitang.user.car.api.model.entity.Car;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.model.request.car.CarCreateRequest;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.service.user.UserLoggedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarCreateServiceTest extends MockitoTestBase {

    @InjectMocks
    CarCreateService carCreateService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserLoggedService userLoggedService;

    @Test
    @DisplayName("create :: should to create new car")
    void should_to_create_new_car() {

        when(userLoggedService.getUserAuthenticated()).thenReturn(new User());
        when(carRepository.findByLicensePlate("aaa-1111")).thenReturn(Optional.empty());

        var car = CarCreateRequest.builder().year(2023).color("preto").licensePlate("aaa-1111").model("argo").build();
        carCreateService.create(car);

        verify(userLoggedService).getUserAuthenticated();
        verify(carRepository).findByLicensePlate("aaa-1111");
    }

    @Test
    @DisplayName("create :: should to valid LicensePlate already exists")
    void should_to_valid_LicensePlate_already_exists() {

        when(userLoggedService.getUserAuthenticated()).thenReturn(new User());
        when(carRepository.findByLicensePlate("aaa-1111")).thenReturn(Optional.of(Car.builder().licensePlate("aaa-1111").build()));

        var car = CarCreateRequest.builder().year(2023).color("preto").licensePlate("aaa-1111").model("argo").build();
        var exception = assertThrows(BadRequestException.class, () -> carCreateService.create(car));
        assertEquals("LicensePlate already exists", exception.getMessage());

        verify(userLoggedService).getUserAuthenticated();
        verify(carRepository).findByLicensePlate("aaa-1111");
    }
}