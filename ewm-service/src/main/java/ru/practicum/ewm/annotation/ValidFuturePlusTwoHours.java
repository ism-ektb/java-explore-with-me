package ru.practicum.ewm.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class ValidFuturePlusTwoHours implements ConstraintValidator<FuturePlusTwoHours, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        return value == null || value.isAfter(LocalDateTime.now().plusHours(2L));
    }

    @Override
    public void initialize(FuturePlusTwoHours constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
