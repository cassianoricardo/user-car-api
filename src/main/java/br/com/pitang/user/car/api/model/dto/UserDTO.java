package br.com.pitang.user.car.api.model.dto;

import br.com.pitang.user.car.api.model.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String fistName;
    private String lastName;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    private Date birtday;
    private String login;
    @JsonIgnore
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
                .build();
    }
}