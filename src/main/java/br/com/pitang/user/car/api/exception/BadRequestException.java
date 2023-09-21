package br.com.pitang.user.car.api.exception;

public class BadRequestException extends RuntimeException{

        public BadRequestException(String errorMessage) {
            super(errorMessage);
        }
}