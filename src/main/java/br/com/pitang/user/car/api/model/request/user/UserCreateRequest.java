package br.com.pitang.user.car.api.model.request.user;


import br.com.pitang.user.car.api.model.dto.CarDTO;
import br.com.pitang.user.car.api.model.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

import static br.com.pitang.user.car.api.util.RegexConstants.EMAIL_PATTERN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    @NotBlank(message = "Missing fistName")
    private String fistName;
    @NotBlank(message = "Missing lastName")
    private String lastName;
    @NotBlank(message = "Missing email")
    @Pattern(regexp = EMAIL_PATTERN, message = "Invalid email")
    private String email;
    @NotNull(message = "Missing birtday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date birtday;
    @NotBlank(message = "Missing login")
    private String login;
    @NotBlank(message = "Missing password")
    private String password;
    @NotBlank(message = "Missing phone")
    private String phone;
    private List<CarDTO> cars;

    public User parseToEntity(){

        return User.builder()
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