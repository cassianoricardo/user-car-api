package br.com.pitang.user.car.api.model.dto.request;


import br.com.pitang.user.car.api.model.User;
import br.com.pitang.user.car.api.model.dto.CarDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    private Long id;
    private String fistName;
    private String lastName;
    private String email;
    private Date birtday;
    private String login;
    private String password;
    private String phone;
    private List<CarDTO> cars;

    public User parseToEntity(){
        return User.builder().id(this.id)
                .fistName(this.fistName)
                .lastName(this.lastName)
                .email(this.email)
                .login(this.login)
                .password(this.password)
                .phone(this.phone)
                .birtday(this.birtday)
                .cars(this.cars.stream().map(CarDTO::parseToEntity).collect(Collectors.toList()))
                .build();
    }
}