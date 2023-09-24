package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.MockitoTestBase;
import br.com.pitang.user.car.api.exception.BadRequestException;
import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.model.request.user.UserUpdateRequest;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserUpdateServiceTest extends MockitoTestBase{

    @InjectMocks
    UserUpdateService userUpdateService;


    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("update :: should to return updated user")
    void should_to_return_updated_user() {

        var userUpdateRequest = UserUpdateRequest.builder().login("ze").password("123")
                                                                 .email("ze@gmail.com").birtday(Date.valueOf("1999-01-01"))
                                                                 .fistName("ze").lastName("carlos").phone("081").build();

        var user = User.builder().login("zezin").password("12")
                             .email("zezin@gmail.com").birtday(Date.valueOf("1998-01-01"))
                             .fistName("zezin").lastName("roberto").phone("087").build();

        var useDTOExpected = UserDTO.builder().login("ze").email("ze@gmail.com")
                .birtday(Date.valueOf("1999-01-01")).fistName("ze").lastName("carlos").phone("081").build();

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(userUpdateRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByLogin(userUpdateRequest.getLogin())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        assertEquals(useDTOExpected, userUpdateService.update(2L, userUpdateRequest));

        verify(userRepository).findById(2L);
        verify(userRepository).findByEmail(userUpdateRequest.getEmail());
        verify(userRepository).findByLogin(userUpdateRequest.getLogin());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("update :: should to return user id Not Found")
    void should_to_return_user_id_not_found() {

        var userUpdateRequest = UserUpdateRequest.builder().login("ze").password("123")
                .email("ze@gmail.com").birtday(Date.valueOf("1999-01-01"))
                .fistName("ze").lastName("carlos").phone("081").build();

        var user = User.builder().login("zezin").password("12")
                .email("zezin@gmail.com").birtday(Date.valueOf("1998-01-01"))
                .fistName("zezin").lastName("roberto").phone("087").build();

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        var notFoundException = assertThrows(NotFoundException.class, () -> userUpdateService.update(2L, userUpdateRequest));
        assertEquals("User id: 2 not found", notFoundException.getMessage());

        verify(userRepository).findById(2L);
        verify(userRepository, never()).findByEmail(userUpdateRequest.getEmail());
        verify(userRepository, never()).findByLogin(userUpdateRequest.getLogin());
        verify(userRepository, never()).save(user);
    }

    @Test
    @DisplayName("update :: should to return email already exists")
    void should_to_return_email_already_exists() {

        var userUpdateRequest = UserUpdateRequest.builder().login("ze").password("123")
                .email("ze@gmail.com").birtday(Date.valueOf("1999-01-01"))
                .fistName("ze").lastName("carlos").phone("081").build();

        var user = User.builder().login("zezin").password("12")
                .email("zezin@gmail.com").birtday(Date.valueOf("1998-01-01"))
                .fistName("zezin").lastName("roberto").phone("087").build();

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(userUpdateRequest.getEmail())).thenReturn(Optional.of(user));

        var badRequestException = assertThrows(BadRequestException.class, () -> userUpdateService.update(2L, userUpdateRequest));
        assertEquals("Email already exists", badRequestException.getMessage());

        verify(userRepository).findById(2L);
        verify(userRepository).findByEmail(userUpdateRequest.getEmail());
        verify(userRepository, never()).findByLogin(userUpdateRequest.getLogin());
        verify(userRepository, never()).save(user);
    }

    @Test
    @DisplayName("update :: should to return login already exists")
    void should_to_return_login_already_exists() {

        var userUpdateRequest = UserUpdateRequest.builder().login("ze").password("123")
                .email("ze@gmail.com").birtday(Date.valueOf("1999-01-01"))
                .fistName("ze").lastName("carlos").phone("081").build();

        var user = User.builder().login("zezin").password("12")
                .email("zezin@gmail.com").birtday(Date.valueOf("1998-01-01"))
                .fistName("zezin").lastName("roberto").phone("087").build();

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(userUpdateRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByLogin(userUpdateRequest.getLogin())).thenReturn(Optional.of(user));

        var badRequestException = assertThrows(BadRequestException.class, ()-> userUpdateService.update(2L, userUpdateRequest));
        assertEquals("Login already exists", badRequestException.getMessage());

        verify(userRepository).findById(2L);
        verify(userRepository).findByEmail(userUpdateRequest.getEmail());
        verify(userRepository).findByLogin(userUpdateRequest.getLogin());
        verify(userRepository, never()).save(user);
    }
}