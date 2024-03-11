package ru.practicum.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.time.LocalDateTime;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class StartIsBeforeEnd implements ConstraintValidator<ValidStartIsBeforeEnd, Object[]> {

    public boolean isValid(Object[] value, ConstraintValidatorContext constraintContext) {
        if (value[0] == null || value[1] == null) {
            return true;
        }
        if (!(value[0] instanceof LocalDateTime)
                || !(value[1] instanceof LocalDateTime)) {
            throw new IllegalArgumentException(
                    "Illegal method signature, expected two parameters of type LocalDate.");
        }
        return ((LocalDateTime) value[0]).isBefore((LocalDateTime) value[1]);
    }
}
