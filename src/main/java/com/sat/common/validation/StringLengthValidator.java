package com.sat.common.validation;

import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@NoArgsConstructor
public class StringLengthValidator implements CustomValidator<StringLength, String> {
    private int min;
    private int max;

    public StringLengthValidator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void initialize(StringLength constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.format("%d이상 %d이하이어야 합니다.", min, max))
                .addConstraintViolation();
        return isValid(value);
    }

    @Override
    public boolean isValid(String value) {
        return value != null && value.length() >= min && value.length() <= max;
    }

    @Override
    public Supplier<? extends RuntimeException> throwIfInvalidValue(String exceptionMessage) {
        return () -> new IllegalArgumentException(exceptionMessage);
    }
}
