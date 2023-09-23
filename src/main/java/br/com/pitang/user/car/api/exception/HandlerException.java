package br.com.pitang.user.car.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class HandlerException {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        List<String> messages = new ArrayList<>();

        ex.getAllErrors().forEach(err -> messages.add(err.getDefaultMessage()));

        String joinMessage = "";

        if(!messages.isEmpty()){
            joinMessage = String.join(", ", messages);
        }

        log.warn("{} - Validation: {}",joinMessage, getRequestTrace());
        return ResponseEntity.status(BAD_REQUEST.value())
                             .body(ErrorMessage.builder()
                                     .errorCode(getRequestTrace())
                                     .message(joinMessage)
                                               .build());

    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException ex) {
        log.error("{} - Log error: {}",ex.getMessage(), getRequestTrace());
        return ResponseEntity.status(NOT_FOUND.value())
                .body(ErrorMessage.builder()
                        .errorCode(getRequestTrace())
                        .message(ex.getMessage())
                        .build());
    }
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorMessage> handleUnauthorizedException(UnauthorizedException ex) {
        log.error("{} - Log error: {}",ex.getMessage(), getRequestTrace());
        return ResponseEntity.status(UNAUTHORIZED.value())
                .body(ErrorMessage.builder()
                        .errorCode(getRequestTrace())
                        .message(ex.getMessage())
                        .build());
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorMessage> handleForbiddenException(ForbiddenException ex) {
        log.error("{} - Log error: {}",ex.getMessage(), getRequestTrace());
        return ResponseEntity.status(FORBIDDEN.value())
                .body(ErrorMessage.builder()
                        .errorCode(getRequestTrace())
                        .message(ex.getMessage())
                        .build());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException ex) {
        log.error("{} - Log error: {}",ex.getMessage(), getRequestTrace());
        return ResponseEntity.status(BAD_REQUEST.value())
                .body(ErrorMessage.builder()
                        .errorCode(getRequestTrace())
                        .message(ex.getMessage())
                        .build());
    }

    private static String getRequestTrace() {
        try {
            return MDC.get("requestTraceId");
        } catch (Exception ex) {
            log.error("Error reading requestTraceId from context. Returning null.", ex);
            return null;
        }
    }
}