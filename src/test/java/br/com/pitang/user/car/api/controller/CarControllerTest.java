package br.com.pitang.user.car.api.controller;

import br.com.pitang.user.car.api.MockMvcBase;
import br.com.pitang.user.car.api.model.dto.CarDTO;
import br.com.pitang.user.car.api.model.request.car.CarCreateRequest;
import br.com.pitang.user.car.api.model.request.car.CarUpdateRequest;
import br.com.pitang.user.car.api.service.car.CarCreateService;
import br.com.pitang.user.car.api.service.car.CarDeleteService;
import br.com.pitang.user.car.api.service.car.CarFindService;
import br.com.pitang.user.car.api.service.car.CarUpdateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarControllerTest extends MockMvcBase {

    @MockBean
    private CarCreateService carCreateService;

    @MockBean
    private CarFindService carFindService;

    @MockBean
    private CarDeleteService carDeleteService;

    @MockBean
    private CarUpdateService carUpdateService;

    //region getCar(id)
    @Test
    @DisplayName("GET /cars/{id} - should to return car by id")
    void should_to_return_car_by_id() throws Exception {

        var carDTO = CarDTO.builder().id(1L).year(2020).color("black").model("tracker").licensePlate("aaa-1234").build();
        when(carFindService.findById(1L)).thenReturn(carDTO);

        performGet("/cars/{id}", 1L).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.year").value(2020))
                .andExpect(jsonPath("$.color").value("black"))
                .andExpect(jsonPath("$.model").value("tracker"))
                .andExpect(jsonPath("$.licensePlate").value("aaa-1234"));

        verify(carFindService).findById(1L);

    }
    //endregion

    //region getAllCars()
    @Test
    @DisplayName("GET /cars - should to return all cars")
    void should_to_return_all_cars() throws Exception {

        var carDTO1 = CarDTO.builder().id(1L).year(2020).color("black").model("tracker").licensePlate("aaa-1234").build();
        var carDTO2 = CarDTO.builder().id(2L).year(2020).color("white").model("tracker").licensePlate("aaa-1235").build();

        when(carFindService.findAll()).thenReturn(List.of(carDTO1,carDTO2));

        performGet("/cars")
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].year").value(2020))
                .andExpect(jsonPath("$.[0].color").value("black"))
                .andExpect(jsonPath("$.[0].model").value("tracker"))
                .andExpect(jsonPath("$.[0].licensePlate").value("aaa-1234"))

                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].year").value(2020))
                .andExpect(jsonPath("$.[1].color").value("white"))
                .andExpect(jsonPath("$.[1].model").value("tracker"))
                .andExpect(jsonPath("$.[1].licensePlate").value("aaa-1235"));

        verify(carFindService).findAll();
    }
    //endregion

    //region updateCar(CarUpdateRequest)
    @Test
    @DisplayName("PUT /cars/{id} - should to update car by id")
    void should_to_update_car_by_id() throws Exception {

        var carDTO = CarDTO.builder().id(1L).year(2020).color("black").model("tracker").licensePlate("aaa-1234").build();

        when(carUpdateService.update(eq(1L), any(CarUpdateRequest.class))).thenReturn(carDTO);

        var carUpdateRequest  = CarUpdateRequest.builder().year(2020).color("black").model("tracker").licensePlate("aaa-1234").build();

        performPut("/cars/{id}", carUpdateRequest, 1L)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.year").value(2020))
                .andExpect(jsonPath("$.color").value("black"))
                .andExpect(jsonPath("$.model").value("tracker"))
                .andExpect(jsonPath("$.licensePlate").value("aaa-1234"));

        verify(carUpdateService).update(eq(1L), any(CarUpdateRequest.class));

    }
    //endregion

    //region deleteCar(id)
    @Test
    @DisplayName("DELETE /cars/{id} - should to delete car by id")
    void should_to_delete_car_by_id() throws Exception {

        performDelete("/cars/{id}",1L)
                .andExpect(status().isNoContent());

        verify(carDeleteService).delete(1L);

    }
    //endregion

    //region createCar(CarCreateRequest)
    @Test
    @DisplayName("POST /cars - should to create new car")
    void should_to_create_new_car() throws Exception {

        var carCreateRequest = CarCreateRequest.builder().year(2020).color("black").model("tracker").licensePlate("aaa-1234").build();

        performPost("/cars", carCreateRequest)
                .andExpect(status().isCreated());

        verify(carCreateService).create(any(CarCreateRequest.class));

    }

    @ParameterizedTest(name="{1}")
    @MethodSource("providerMissingFieldsCreateCar")
    @DisplayName("POST /cars - should to valid missing fields")
    void should_to_valid_missing_fields(CarCreateRequest carCreateRequest, String expectedMessage) throws Exception {

        performPost("/cars", carCreateRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));

        verifyNoInteractions(carCreateService);
    }

    private static Stream<Arguments> providerMissingFieldsCreateCar() {

        var missingYear = CarCreateRequest.builder().color("black").model("tracker").licensePlate("aaa-1234").build();
        var missingColor = CarCreateRequest.builder().year(2020).model("tracker").licensePlate("aaa-1234").build();
        var missingModel = CarCreateRequest.builder().year(2020).color("black").licensePlate("aaa-1234").build();
        var missingLicensePlate = CarCreateRequest.builder().year(2020).color("black").model("tracker").build();

        return Stream.of(
                Arguments.of(missingYear, "Missing year"),
                Arguments.of(missingColor, "Missing color"),
                Arguments.of(missingModel, "Missing model"),
                Arguments.of(missingLicensePlate, "Missing licensePlate"));
    }

    @Test
    @DisplayName("PUT /cars/{id}/photo - should to updated photo of user")
    void should_to_updated_photo_of_user() throws Exception {

        var inputStream = new FileInputStream(getClass().getResource("/photo-test.png").getFile());
        var multiPartFile = new MockMultipartFile("Template", inputStream);

        performMultipart(HttpMethod.PUT,"/cars/{id}/photo", "photo", multiPartFile.getBytes(), 1L).andExpect(status().isOk());
        verify(carUpdateService).updatePhoto( any(MultipartFile.class), eq(1L));
    }
    //endregion
}