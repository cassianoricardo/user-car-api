package br.com.pitang.user.car.api.exception;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String errorMessage) {
        super(errorMessage);
    }
}
