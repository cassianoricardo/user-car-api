package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.MockitoTestBase;
import br.com.pitang.user.car.api.exception.BadRequestException;
import br.com.pitang.user.car.api.model.dto.CarDTO;
import br.com.pitang.user.car.api.model.entity.Car;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.model.request.user.UserCreateRequest;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class UserCreateServiceTest extends MockitoTestBase {

    @InjectMocks
    private UserCreateService userCreateService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("create :: should to return email already exists")
    void should_to_return_email_already_exists() {

        var carDTO= CarDTO.builder()
                .model("pulse")
                .year(2020)
                .color("preto")
                .licensePlate("aaa-1324")
                .build();

        var carsDTO = List.of(carDTO);

        var userCreateRequest = UserCreateRequest
                .builder().login("ze").password("123").email("ze@gmail.com")
                .birtday(Date.valueOf("1999-01-01")).firstName("ze")
                .lastName("carlos").phone("081")
                .cars(carsDTO)
                .build();

        when(userRepository.findByEmail(userCreateRequest.getEmail())).thenReturn(Optional.of(new User()));

        var exception = assertThrows(BadRequestException.class, ()-> userCreateService.create(userCreateRequest));
        assertEquals("Email already exists", exception.getMessage());

        verify(userRepository).findByEmail(userCreateRequest.getEmail());
        verify(userRepository, never()).findByLogin(userCreateRequest.getLogin());
        verify(passwordEncoder, never()).encode(userCreateRequest.getPassword());
        verify(userRepository, never()).save(any(User.class));
        verify(carRepository, never()).findByLicensePlate("aaa-1324");
        verify(carRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("create :: should to return login already exists")
    void should_to_return_login_already_exists() {

        var carDTO= CarDTO.builder()
                .model("pulse")
                .year(2020)
                .color("preto")
                .licensePlate("aaa-1324")
                .build();

        var carsDTO = List.of(carDTO);

        var userCreateRequest = UserCreateRequest
                .builder().login("ze").password("123").email("ze@gmail.com")
                .birtday(Date.valueOf("1999-01-01")).firstName("ze")
                .lastName("carlos").phone("081")
                .cars(carsDTO)
                .build();

        when(userRepository.findByEmail(userCreateRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByLogin(userCreateRequest.getLogin())).thenReturn(Optional.of(new User()));

        var exception = assertThrows(BadRequestException.class, ()-> userCreateService.create(userCreateRequest));
        assertEquals("Login already exists", exception.getMessage());

        verify(userRepository).findByEmail(userCreateRequest.getEmail());
        verify(userRepository).findByLogin(userCreateRequest.getLogin());
        verify(passwordEncoder, never()).encode(userCreateRequest.getPassword());
        verify(userRepository, never()).save(any(User.class));
        verify(carRepository,  never()).findByLicensePlate("aaa-1324");
        verify(carRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("create :: should to return license Plate already exists")
    void should_to_return_license_plate_already_exists() {

        var carDTO= CarDTO.builder()
                .model("pulse")
                .year(2020)
                .color("preto")
                .licensePlate("aaa-1324")
                .build();

        var carsDTO = List.of(carDTO);

        var userCreateRequest = UserCreateRequest
                .builder().login("ze").password("123").email("ze@gmail.com")
                .birtday(Date.valueOf("1999-01-01")).firstName("ze")
                .lastName("carlos").phone("081")
                .cars(carsDTO)
                .build();

        when(userRepository.findByEmail(userCreateRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByLogin(userCreateRequest.getLogin())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userCreateRequest.getPassword())).thenReturn("encripted password");
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(carRepository.findByLicensePlate("aaa-1324")).thenReturn(Optional.of(new Car()));

        var exception = assertThrows(BadRequestException.class, ()-> userCreateService.create(userCreateRequest));
        assertEquals("License Plate already exists", exception.getMessage());

        verify(userRepository).findByEmail(userCreateRequest.getEmail());
        verify(userRepository).findByLogin(userCreateRequest.getLogin());
        verify(passwordEncoder).encode(userCreateRequest.getPassword());
        verify(userRepository).save(any(User.class));
        verify(carRepository).findByLicensePlate("aaa-1324");
        verify(carRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("create :: should to create new user")
    void should_to_create_new_user() {

        var carDTO= CarDTO.builder()
                .model("pulse")
                .year(2020)
                .color("preto")
                .licensePlate("aaa-1324")
                .build();

        var carsDTO = List.of(carDTO);

        var car = carDTO.parseToEntity();

        var userCreateRequest = UserCreateRequest
                .builder().login("ze").password("123").email("ze@gmail.com")
                .birtday(Date.valueOf("1999-01-01")).firstName("ze")
                .lastName("carlos").phone("081")
                .cars(carsDTO)
                .build();

        when(userRepository.findByEmail(userCreateRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByLogin(userCreateRequest.getLogin())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userCreateRequest.getPassword())).thenReturn("encripted password");
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(carRepository.findByLicensePlate("aaa-1324")).thenReturn(Optional.empty());
        when(carRepository.saveAll(anyList())).thenReturn(List.of(car));

        userCreateService.create(userCreateRequest);

        verify(userRepository).findByEmail(userCreateRequest.getEmail());
        verify(userRepository).findByLogin(userCreateRequest.getLogin());
        verify(passwordEncoder).encode(userCreateRequest.getPassword());
        verify(userRepository).save(any(User.class));
        verify(carRepository).findByLicensePlate("aaa-1324");
        verify(carRepository).saveAll(anyList());
    }
}