package br.com.pitang.user.car.api.controller.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
@Builder
public class LoginRequest {
	@NotBlank
  	private String login;

	@NotBlank
	private String password;

}
