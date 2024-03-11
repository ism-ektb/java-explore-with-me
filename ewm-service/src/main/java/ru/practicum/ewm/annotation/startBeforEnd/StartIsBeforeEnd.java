package ru.practicum.ewm.annotation.startBeforEnd;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.time.LocalDateTime;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class StartIsBeforeEnd implements ConstraintValidator<ValidStartIsBeforeEnd, Object[]> {

    public boolean isValid(Object[] value, ConstraintValidatorContext constraintContext) {
        if (value[3] == null || value[4] == null) {
            return true;
        }
        if (!(value[3] instanceof LocalDateTime)
                || !(value[4] instanceof LocalDateTime)) {
            throw new IllegalArgumentException(
                    "Illegal method signature, expected two parameters of type LocalDate.");
        }
        return ((LocalDateTime) value[3]).isBefore((LocalDateTime) value[4]);
    }
}
