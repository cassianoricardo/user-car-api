package br.com.pitang.user.car.api.annotation.atleastoneof;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AtLeastOneOfValidator.class})
public @interface AtLeastOneOf {

    String message() default "falhou";

    String[] fields();
}