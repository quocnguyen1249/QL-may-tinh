package com.qlmaytinh.Util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidationUtil {

    private static final ValidatorFactory factory =
            Validation.buildDefaultValidatorFactory();

    private static final Validator validator = factory.getValidator();

    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
        		ConstraintViolation<T> violation = violations.iterator().next();
        		throw new RuntimeException(violation.getMessage());
        }
    }
}
