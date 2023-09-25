package br.com.pitang.user.car.api.controller;

import br.com.pitang.user.car.api.MockMvcBase;
import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.model.request.user.UserCreateRequest;
import br.com.pitang.user.car.api.model.request.user.UserUpdateRequest;
import br.com.pitang.user.car.api.service.user.UserCreateService;
import br.com.pitang.user.car.api.service.user.UserDeleteService;
import br.com.pitang.user.car.api.service.user.UserFindService;
import br.com.pitang.user.car.api.service.user.UserUpdateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends MockMvcBase {

    @MockBean
    UserUpdateService userUpdateService;

    @MockBean
    UserCreateService userCreateService;

    @MockBean
    UserFindService userFindService;

    @MockBean
    UserDeleteService userDeleteService;


    //region getAllUsers()
    @Test
    @DisplayName("GET /users - should to return all users")
    void should_to_return_all_users() throws Exception {

        var birtday = Calendar.getInstance();
        birtday.set(Calendar.YEAR, 1997);
        birtday.set(Calendar.MONTH, 9);
        birtday.set(Calendar.DATE, 7);

        var userDTOList = List.of(UserDTO.builder().id(1L)
                .email("ze@gmail.com")
                .phone("081")
                .fistName("zé")
                .lastName("carlos")
                .birtday(new Date(birtday.getTime().getTime()))
                .login("ze")
                .build());
        List<UserDTO> userDTOListExpected = new ArrayList<>();

        copyProperties(userDTOList, userDTOListExpected);

        when(userFindService.findAll()).thenReturn(userDTOList);
        performGet("/users")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].email").value("ze@gmail.com"))
                .andExpect(jsonPath("$.[0].phone").value("081"))
                .andExpect(jsonPath("$.[0].fistName").value("zé"))
                .andExpect(jsonPath("$.[0].lastName").value("carlos"))
                .andExpect(jsonPath("$.[0].login").value("ze"))
                .andExpect(jsonPath("$.[0].birtday").value("07/10/1997"));

        verify(userFindService).findAll();
    }
    //endregion

    //region getUser(id)
    @Test
    @DisplayName("GET /users/{id} - should return user by id")
    void should_to_return_user_by_id() throws Exception {

        var birtday = Calendar.getInstance();
        birtday.set(Calendar.YEAR, 1997);
        birtday.set(Calendar.MONTH, 9);
        birtday.set(Calendar.DATE, 7);

        var userDTO = UserDTO.builder().id(1L)
                .email("ze@gmail.com")
                .phone("081")
                .fistName("zé")
                .lastName("carlos")
                .birtday(new Date(birtday.getTime().getTime()))
                .login("ze")
                .build();

        var userDTOExpected = new UserDTO();

        copyProperties(userDTO, userDTOExpected);

        when(userFindService.findById(1L)).thenReturn(userDTO);
        performGet("/users/{id}", 1L)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("ze@gmail.com"))
                .andExpect(jsonPath("$.phone").value("081"))
                .andExpect(jsonPath("$.fistName").value("zé"))
                .andExpect(jsonPath("$.lastName").value("carlos"))
                .andExpect(jsonPath("$.login").value("ze"))
                .andExpect(jsonPath("$.birtday").value("07/10/1997"));

        verify(userFindService).findById(1L);
    }
    //endregion

    //region deleteUser(id)
    @Test
    @DisplayName("Delete /users/{id} - should to delete user by id")
    void should_to_delete_user_by_id() throws Exception {
        performDelete("/users/{id}", 1L).andExpect(status().isNoContent());
        verify(userDeleteService).deleteById(1L);
    }
    //endregion

    //region updateUser(id)
    @Test
    @DisplayName("Put /users/{id} - should to update user by id")
    void should_to_update_user_by_id() throws Exception {

        var birtday = Calendar.getInstance();
        birtday.set(Calendar.YEAR, 1997);
        birtday.set(Calendar.MONTH, 9);
        birtday.set(Calendar.DATE, 7);

        var userUpdateRequest = UserUpdateRequest.builder()
                .email("ze@gmail.com")
                .phone("081")
                .fistName("zé")
                .lastName("carlos")
                .birtday(new Date(birtday.getTime().getTime()))
                .login("ze")
                .build();

        var userDTO = new UserDTO();
        copyProperties(userUpdateRequest, userDTO);

        when(userUpdateService.update(eq(1L), any(UserUpdateRequest.class))).thenReturn(userDTO);

        performPut("/users/{id}", userUpdateRequest, 1L)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("ze@gmail.com"))
                .andExpect(jsonPath("$.phone").value("081"))
                .andExpect(jsonPath("$.fistName").value("zé"))
                .andExpect(jsonPath("$.lastName").value("carlos"))
                .andExpect(jsonPath("$.login").value("ze"))
                .andExpect(jsonPath("$.birtday").value("07/10/1997"));

        verify(userUpdateService).update(eq(1L), any(UserUpdateRequest.class));
    }

    @Test
    @DisplayName("Put /users/{id} - should to valid invalid email")
    void should_to_valid_invalid_email_method_put() throws Exception {

        var invalidEmail = UserCreateRequest.builder()
                .email("ze@gmailcom")
                .build();

        performPut("/users/{id}", invalidEmail, 1L)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid email"));

        verifyNoInteractions(userCreateService);
    }
    //endregion

    //region createUser(UserCreateRequest)
    @Test
    @DisplayName("Post /users - should to create new user")
    void should_to_create_new_user() throws Exception {

        var birtday = Calendar.getInstance();
        birtday.set(Calendar.YEAR, 1997);
        birtday.set(Calendar.MONTH, 9);
        birtday.set(Calendar.DATE, 7);

        var userCreateRequest = UserCreateRequest.builder()
                .email("ze@gmail.com")
                .phone("081")
                .fistName("zé")
                .lastName("carlos")
                .birtday(new Date(birtday.getTime().getTime()))
                .login("ze")
                .password("123")
                .build();

        performPost("/users", userCreateRequest)
                .andExpect(status().isCreated());

        verify(userCreateService).create(any(UserCreateRequest.class));
    }

    @ParameterizedTest(name= "{1}")
    @MethodSource("providerMissingFieldsCreateUser")
    @DisplayName("Post /users - should to valid missing fields")
    void should_to_valid_missing_fields(UserCreateRequest userCreateRequest, String expectedMessage) throws Exception {

        performPost("/users", userCreateRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));

        verifyNoInteractions(userCreateService);
    }

    @Test
    @DisplayName("Post /users - should to valid invalid email")
    void should_to_valid_invalid_email_method_post() throws Exception {

        var birtday = Calendar.getInstance();
        birtday.set(Calendar.YEAR, 1997);
        birtday.set(Calendar.MONTH, 9);
        birtday.set(Calendar.DATE, 7);

        var invalidEmail = UserCreateRequest.builder()
                .email("ze@gmailcom")
                .birtday(new Date(birtday.getTime().getTime()))
                .phone("081")
                .fistName("zé")
                .lastName("carlos")
                .login("ze")
                .password("123")
                .build();

        performPost("/users", invalidEmail)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid email"));

        verifyNoInteractions(userCreateService);
    }
    private static Stream<Arguments> providerMissingFieldsCreateUser() {

        var birtday = Calendar.getInstance();
        birtday.set(Calendar.YEAR, 1997);
        birtday.set(Calendar.MONTH, 9);
        birtday.set(Calendar.DATE, 7);

        var missingBirtday = UserCreateRequest.builder()
                .email("ze@gmail.com")
                .phone("081")
                .fistName("zé")
                .lastName("carlos")
                .login("ze")
                .password("123")
                .build();

        var missingEmail = UserCreateRequest.builder()
                .birtday(new Date(birtday.getTime().getTime()))
                .phone("081")
                .fistName("zé")
                .lastName("carlos")
                .login("ze")
                .password("123")
                .build();

        var missingPhone = UserCreateRequest.builder()
                .email("ze@gmail.com")
                .birtday(new Date(birtday.getTime().getTime()))
                .fistName("zé")
                .lastName("carlos")
                .login("ze")
                .password("123")
                .build();

        var missingFistName = UserCreateRequest.builder()
                .email("ze@gmail.com")
                .birtday(new Date(birtday.getTime().getTime()))
                .phone("081")
                .lastName("carlos")
                .login("ze")
                .password("123")
                .build();

        var missingLastName = UserCreateRequest.builder()
                .email("ze@gmail.com")
                .birtday(new Date(birtday.getTime().getTime()))
                .phone("081")
                .fistName("zé")
                .login("ze")
                .password("123")
                .build();

        var missinglogin = UserCreateRequest.builder()
                .email("ze@gmail.com")
                .birtday(new Date(birtday.getTime().getTime()))
                .phone("081")
                .fistName("zé")
                .lastName("carlos")
                .password("123")
                .build();

        var missingPassword = UserCreateRequest.builder()
                .email("ze@gmail.com")
                .birtday(new Date(birtday.getTime().getTime()))
                .phone("081")
                .fistName("zé")
                .lastName("carlos")
                .login("ze")
                .build();

        return Stream.of(Arguments.of(missingBirtday, "Missing birtday"),
                Arguments.of(missingEmail, "Missing email"),
                Arguments.of(missingPhone, "Missing phone"),
                Arguments.of(missingFistName, "Missing fistName"),
                Arguments.of(missingLastName, "Missing lastName"),
                Arguments.of(missinglogin, "Missing login"),
                Arguments.of(missingPassword, "Missing password"));
    }

    //endregion

    //region uploadPhoto()
    @Test
    @DisplayName("PUT /users/{id}/photo")
    void uploadPhoto() throws Exception {

        var inputStream = new FileInputStream(getClass().getResource("/photo-test.png").getFile());
        var multiPartFile = new MockMultipartFile("Template", inputStream);

        performMultipart(HttpMethod.PUT,"/users/{id}/photo", "photo", multiPartFile.getBytes(), 1L).andExpect(status().isOk());
        verify(userUpdateService).updatePhoto( any(MultipartFile.class), eq(1L));
    }
    //endregion

}