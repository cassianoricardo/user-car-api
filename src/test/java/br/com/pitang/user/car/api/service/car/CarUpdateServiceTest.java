package br.com.pitang.user.car.api.service.car;

import br.com.pitang.user.car.api.MockitoTestBase;
import br.com.pitang.user.car.api.exception.BadRequestException;
import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.dto.CarDTO;
import br.com.pitang.user.car.api.model.entity.Car;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.model.request.car.CarUpdateRequest;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.service.user.UserLoggedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CarUpdateServiceTest extends MockitoTestBase{

    @InjectMocks
    private CarUpdateService carUpdateService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserLoggedService userLoggedService;

    @Test
    @DisplayName("update :: should to return updated user ")
    void should_to_return_update_user(){

        var carUpdateRequest = CarUpdateRequest.builder().year(2020).model("pulse").licensePlate("aaa-111").color("preto").build();

        var user = User.builder().id(2L).build();
        var car = Car.builder().id(1L).user(user).year(2021).model("argo").licensePlate("bbb-2222").color("branco").build();

        var carDTOExpected = CarDTO.builder().id(1L).year(2020).model("pulse").licensePlate("aaa-111").color("preto").build();

        when(userLoggedService.getUserAuthenticated()).thenReturn(user);
        when(carRepository.findByIdAndUserId(1L, 2L)).thenReturn(Optional.of(car));
        when(carRepository.findByLicensePlateAndUserId(carUpdateRequest.getLicensePlate(), 2L)).thenReturn(Optional.empty());
        when(carRepository.save(car)).thenReturn(car);

        assertEquals(carDTOExpected, carUpdateService.update(1L, carUpdateRequest));

        verify(userLoggedService).getUserAuthenticated();
        verify(carRepository).findByIdAndUserId(1L, 2L);
        verify(carRepository).findByLicensePlateAndUserId(carUpdateRequest.getLicensePlate(), 2L);
        verify(carRepository).save(car);
    }

    @Test
    @DisplayName("update :: should to return license plate already exists")
    void should_to_return_license_plate_already_exists(){

        var carUpdateRequest = CarUpdateRequest.builder().year(2020).model("pulse").licensePlate("aaa-111").color("preto").build();

        var user = User.builder().id(2L).build();
        var car = Car.builder().id(1L).user(user).year(2021).model("argo").licensePlate("bbb-2222").color("branco").build();

        when(userLoggedService.getUserAuthenticated()).thenReturn(user);
        when(carRepository.findByIdAndUserId(1L, 2L)).thenReturn(Optional.of(car));
        when(carRepository.findByLicensePlateAndUserId(carUpdateRequest.getLicensePlate(), 2L)).thenReturn(Optional.of(new Car()));

        var badRequestException = assertThrows(BadRequestException.class, () -> carUpdateService.update(1L, carUpdateRequest));
        assertEquals("License Plate already exists", badRequestException.getMessage());

        verify(userLoggedService).getUserAuthenticated();
        verify(carRepository).findByIdAndUserId(1L, 2L);
        verify(carRepository).findByLicensePlateAndUserId(carUpdateRequest.getLicensePlate(), 2L);
        verify(carRepository, never()).save(car);
    }

    @Test
    @DisplayName("update :: should to return car id: 1 not found")
    void should_to_return_car_id_not_found(){

        var carUpdateRequest = CarUpdateRequest.builder().year(2020).model("pulse").licensePlate("aaa-111").color("preto").build();

        var user = User.builder().id(2L).build();

        when(userLoggedService.getUserAuthenticated()).thenReturn(user);
        when(carRepository.findByIdAndUserId(1L, 2L)).thenReturn(Optional.empty());

        var notFoundException = assertThrows(NotFoundException.class, () -> carUpdateService.update(1L, carUpdateRequest));
        assertEquals("car id: 1 not found", notFoundException.getMessage());

        verify(userLoggedService).getUserAuthenticated();
        verify(carRepository).findByIdAndUserId(1L, 2L);
        verify(carRepository,  never()).findByLicensePlateAndUserId(carUpdateRequest.getLicensePlate(), 2L);
        verify(carRepository, never()).save(any());
    }
}