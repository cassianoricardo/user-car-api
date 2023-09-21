package br.com.pitang.user.car.api.model.request.user;

import br.com.pitang.user.car.api.annotation.atleastoneof.AtLeastOneOf;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;

import static br.com.pitang.user.car.api.util.RegexConstants.EMAIL_PATTERN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@AtLeastOneOf(fields = {"fistName", "lastName", "email", "birtday", "login", "password", "phone"},
message = "Enter at least one of the fields fistName, lastName, email, birtday, login, password, phone")
public class UserUpdateRequest {


    private String fistName;
    private String lastName;

    @Pattern(regexp = EMAIL_PATTERN, message = "Invalid email")
    private String email;

    private Date birtday;

    private String login;

    private String password;

    private String phone;

}