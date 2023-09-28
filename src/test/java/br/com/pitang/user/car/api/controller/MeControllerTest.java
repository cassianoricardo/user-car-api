package br.com.pitang.user.car.api.controller;

import br.com.pitang.user.car.api.MockMvcBase;
import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.model.response.MeResponse;
import br.com.pitang.user.car.api.service.me.MeInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MeControllerTest extends MockMvcBase {

    @MockBean
    MeInfoService meInfoService;

    //region getInfo()
    @Test
    @DisplayName("GET /me - should return info me")
    void should_to_return_info_me() throws Exception {
        var me = MeResponse.builder()
                                      .user(UserDTO.builder()
                                                   .id(1L).email("ze@gmail.com")
                                                   .phone("081")
                                                   .fistName("zé")
                                                   .lastName("carlos")
                                                   //.birtday(Date.valueOf("1997-10-07"))
                                                   .login("ze").build())
                                      .lastLogin(Date.valueOf("1997-10-07"))
                                      .createdAt(Date.valueOf("1997-10-07"))
                                      .build();

        when(meInfoService.getInfo()).thenReturn(me);
        performGet("/me")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.email").value("ze@gmail.com"))
                .andExpect(jsonPath("$.user.phone").value("081"))
                .andExpect(jsonPath("$.user.fistName").value("zé"))
                .andExpect(jsonPath("$.user.lastName").value("carlos"))
                .andExpect(jsonPath("$.user.login").value("ze"))
                //.andExpect(jsonPath("$.user.birtday").value("07/10/1997"))
                .andExpect(jsonPath("$.createdAt").value("07/10/1997"))
                .andExpect(jsonPath("$.lastLogin").value("07/10/1997"));

        verify(meInfoService).getInfo();
    }
    //endregion
}