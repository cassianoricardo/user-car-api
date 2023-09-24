package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.MockitoTestBase;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

class UserDeleteServiceTest extends MockitoTestBase {

    @InjectMocks
    UserDeleteService userDeleteService;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("deleteById ::  should to delete user by id")
    void should_to_delete_user_by_id() {

        userDeleteService.deleteById(1L);
        verify(userRepository).deleteById(1L);
    }
}