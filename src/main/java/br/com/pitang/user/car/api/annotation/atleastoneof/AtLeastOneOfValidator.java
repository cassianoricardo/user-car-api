package br.com.pitang.user.car.api.annotation.atleastoneof;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.util.Optional;

public class AtLeastOneOfValidator implements ConstraintValidator<AtLeastOneOf, Object> {

    private String[] fields;
    private String message;

    @Override
    public void initialize(AtLeastOneOf annotation) {
        this.fields = annotation.fields();
        this.message = annotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

        int matches = countNumberOfMatches(wrapper);

        if (matches == 0) {
            setValidationErrorMessage(context, message);
            return false;
        }

        return true;
    }

    private int countNumberOfMatches(BeanWrapper wrapper) {
        int matches = 0;
        for (String field : this.fields) {
            Object value = wrapper.getPropertyValue(field);
            boolean isPresent = detectOptionalValue(value);

            if (value != null && isPresent) {
                matches++;
            }
        }
        return matches;
    }

    @SuppressWarnings("rawtypes")
    private boolean detectOptionalValue(Object value) {
        if (value instanceof Optional) {
            return ((Optional) value).isPresent();
        }
        return true;
    }

    private void setValidationErrorMessage(ConstraintValidatorContext context, String template) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("{" + template + "}").addConstraintViolation();
    }
}