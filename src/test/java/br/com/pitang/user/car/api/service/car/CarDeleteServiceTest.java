package br.com.pitang.user.car.api.service.car;

import br.com.pitang.user.car.api.MockitoTestBase;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.service.user.UserLoggedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarDeleteServiceTest extends MockitoTestBase {


    @InjectMocks
    private CarDeleteService carDeleteService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserLoggedService userLoggedService;


    @Test
    @DisplayName("delete :: should to delete car by id")
    void should_to_delete_car_by_id() {

        var user = User.builder().id(1L).build();
        when(userLoggedService.getUserAuthenticated()).thenReturn(user);

        assertDoesNotThrow(() -> carDeleteService.delete(2L));

        verify(userLoggedService).getUserAuthenticated();
        verify(carRepository).deleteByIdAndUserId(2L, 1L);
    }
}