package br.com.pitang.user.car.api.exception;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
