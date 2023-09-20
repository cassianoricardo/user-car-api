package br.com.pitang.user.car.api.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private Instant timeStamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
