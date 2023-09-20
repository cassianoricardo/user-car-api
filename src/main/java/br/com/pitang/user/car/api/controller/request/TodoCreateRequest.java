package br.com.pitang.user.car.api.controller.request;

import br.com.pitang.user.car.api.enums.StatusTaskEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class TodoCreateRequest {

    @NotBlank(message = "summary is mandatory")
    private String summary;

    @NotBlank(message = "description is mandatory")
    private String description;

    @NotNull(message = "status is mandatory")
    private StatusTaskEnum status;
}
