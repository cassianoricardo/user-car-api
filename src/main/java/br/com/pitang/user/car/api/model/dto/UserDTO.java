package br.com.pitang.user.car.api.model.dto.request;

import br.com.pitang.user.car.api.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String fistName;
    private String lastName;
    private String email;
    private Date birtday;
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String phone;

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