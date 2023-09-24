package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.MockitoTestBase;
import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.dto.UserDTO;
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
        when(carRepository.findByUserId(1L)).thenReturn(emptyList());
        assertEquals(userExpected, userFindService.findById(1L));
        verify(userRepository).findById(1L);
        verify(carRepository).findByUserId(1L);
    }

    @Test
    @DisplayName("findById :: should to return user not found")
    void should_to_return_user_not_found() {

        var userExpected = UserDTO.builder().id(1L).cars(emptyList()).build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> assertEquals(userExpected, userFindService.findById(1L)));
        assertEquals("User Not Found", exception.getMessage());
        verify(userRepository).findById(1L);
        verify(carRepository, never()).findByUserId(1L);
    }

    @Test
    @DisplayName("findAll :: should to return all users")
    void should_to_return_all_users() {

        var user1 = User.builder().id(1L).build();
        var user2 = User.builder().id(2L).build();

        when(userRepository.findAll()).thenReturn(List.of(user1,user2));
        when(carRepository.findByUserId(anyLong())).thenReturn(emptyList());

        userFindService.findAll();

        verify(userRepository).findAll();
        verify(carRepository, times(2)).findByUserId(anyLong());
    }
}