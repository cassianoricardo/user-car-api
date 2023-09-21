package br.com.pitang.user.car.api.model.request.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
	@NotBlank
  	private String login;

	@NotBlank
	private String password;

}
